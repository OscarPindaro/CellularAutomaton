import operator, random
from deap import algorithms, base, creator, tools, gp
from multiprocessing import Pool
import numpy as np
import json
from communication import *
import sys

class JsonParser:

    def __init__(self):
        pass

    def parsePopulation(self, data,pset):
        jsonData = json.loads(data)
        population = []

        for prey in jsonData:
            listOfTrees = []
            for tree in jsonData[prey]["functions"]:
                listOfTrees.append(gp.PrimitiveTree.from_string(tree, pset))
            individual = creator.Individual(listOfTrees)
            individual.indName = prey
            population.append(individual)
        return population

    #a dictionary where the key is the name of an individual and the value is its fitness
    def parseFitness(self, data):
        fitnesses = json.loads(data)
        return fitnesses

    #returns a dictionary
    def parseInputParams(self, data):
        return json.loads(data)

    def dumpPopulationToJson(self, population):
        popDict = {}
        for individual in population:
            functions = [str(f) for f in individual]
            popDict[individual.indName] = {}
            popDict[individual.indName]["functions"] = functions
        return json.dumps(popDict)


# communication = communication.Communication(12346)
# a = communication.readParameters()
# print(a)
# exit()


def evaluateIndividual(individual, fitnessDictionary):
    return fitnessDictionary[individual.indName]["fitness"],


class GeneticAlgorithm:
    MAX_HEIGHT = 30

    def __init__(self, cxpb, mutpb, popSize):
        self.toolbox = base.Toolbox()
        self.pset = None
        #probably they will be a dictionary
        self.population = None
        self.fitness = None
        self.cxpb = cxpb
        self.mutpb = mutpb
        self.stats = None
        self.fitnessDictionary = {}
        self.populationSize = popSize

    def createPrimitiveSet(self, nOfArguments):
        self.pset = gp.PrimitiveSet(name="decisionTree", arity=nOfArguments)
        self.pset.addPrimitive(operator.add, arity=2)
        self.pset.addPrimitive(operator.sub, arity=2)
        self.pset.addPrimitive(operator.mul, arity=2)
        self.pset.addEphemeralConstant("const", lambda: random.uniform(-1, 1))

    def setCreator(self):
        creator.create("FitnessMax", base.Fitness, weights=(1.0,))
        creator.create("DecisionFunction", gp.PrimitiveTree)
        creator.create("Individual", list, fitness=creator.FitnessMax, indName=None)

    def enableMultiprocessing(self):
        pool = Pool()
        self.toolbox.register("map", pool.map)

    def registerGeneratorFunctions(self, nOfFunctions, minHeight=1, maxHeight = 5):
        self.toolbox.register("expr", gp.genFull, pset=self.pset, min_=minHeight, max_=maxHeight)
        self.toolbox.register("tree", tools.initIterate, creator.DecisionFunction, self.toolbox.expr)
        self.toolbox.register("trees", tools.initRepeat, list, self.toolbox.tree, n = nOfFunctions)
        self.toolbox.register("individual", tools.initIterate, creator.Individual, self.toolbox.trees)
        self.toolbox.register("population", tools.initRepeat, list, self.toolbox.individual)

    def registerEvolutionFunctions(self, sizeTournament = 4, minGen=0, maxGen=2):
        self.toolbox.register("evaluate", evaluateIndividual, fitnessDictionary = self.fitnessDictionary)
        self.toolbox.register("mate", gp.cxOnePoint)
        self.toolbox.register("expr_mut", gp.genFull, pset=self.pset, min_=minGen,max_= maxGen)
        self.toolbox.register("mutate", gp.mutUniform, expr=self.toolbox.expr_mut, pset=self.pset)
        self.toolbox.register("select", tools.selTournament, tournsize=3)
        #fix tree height
        self.toolbox.decorate("mate", gp.staticLimit(key=operator.attrgetter("height"), max_value=self.MAX_HEIGHT))
        self.toolbox.decorate("mutate", gp.staticLimit(key=operator.attrgetter("height"), max_value=self.MAX_HEIGHT))

    def setStatistics(self):
        self.stats = tools.Statistics(key=operator.attrgetter("fitness.values"))
        self.stats.register("max", np.max)
        self.stats.register("min", np.min)
        self.stats.register("mean", np.mean)
        self.stats.register("std", np.std)
        self.stats.register("median", np.median)

    def setPopulation(self, population):
        self.population = population

    def createRandomPopulation(self, size):
        self.population = self.toolbox.population(size)

    def computeFitness(self):
        fitnesses = self.toolbox.map(self.toolbox.evaluate, self.population)
        for (ind, fit) in zip(self.population, fitnesses):
            ind.fitness.values = fit

    def selectIndividuals(self):
        if not self.population:
            self.population = self.toolbox.population(self.populationSize)
        self.population = self.toolbox.select(self.population, k=self.populationSize)
        self.population = [self.toolbox.clone(ind) for ind in self.population]


    def crossover(self):
        for child1, child2 in zip(self.population[::2], self.population[1::2]):
            if random.random() < self.cxpb:
                tree1 = random.choice(child1)
                index1 = child1.index(tree1)
                tree2 = random.choice(child2)
                index2 = child2.index(tree2)
                # newTree1, newTree2 = self.toolbox.mate(tree1, tree2)
                newTree1, newTree2 = gp.cxOnePoint(tree1, tree2)
                child1[index1] = newTree1
                child2[index2] = newTree2
                del child1.fitness.values, child2.fitness.values

    def mutate(self):
        for mutant in self.population:
            if random.random() < self.mutpb:
                tree = random.choice(mutant)
                index = mutant.index(tree)
                self.toolbox.mutate(tree)
                mutant[index] = tree
                del mutant.fitness.values

    def resetNames(self, names):

        for individual, name in zip(self.population, names):
            #print(individual, name)
            individual.indName = name

    def setFitnessDictionary(self, dict):
        ga.fitnessDictionary = dict
        self.toolbox.unregister("evaluate")
        self.toolbox.register("evaluate", evaluateIndividual, fitnessDictionary = self.fitnessDictionary)

if __name__ == "__main__":
    com= Communication(int(sys.argv[1]))
    parser = JsonParser()
    params = com.readParameters()
    params = parser.parseInputParams(params)
    ga = GeneticAlgorithm(params["cxpb"], params["mutpb"], params["populationSize"])
    print(params["populationSize"])
    print(params["nameOfEntity"])
    ga.setCreator()



    ga.createPrimitiveSet(params["numberOfInputs"])
    #ga.enableMultiprocessing()
    ga.registerGeneratorFunctions(params["nOfFunctions"], minHeight=1, maxHeight = 3)
    ga.registerEvolutionFunctions()
    ga.setStatistics()



    populationJson = com.readPopulation()
    population = parser.parsePopulation(populationJson, ga.pset)
    populationNames = [ individual.indName for individual in population ]
    ga.setPopulation(population)
    ngen = 0
    stop = False

    while(not stop):
        jsonFitness = com.readFitness()
        fitness = parser.parseFitness(jsonFitness)
        numberFitness = [ fitness[prey]["fitness"] for prey in fitness]
        ga.setFitnessDictionary(fitness)
        ga.computeFitness()
        record = ga.stats.compile(ga.population)
        print(record)
        ga.selectIndividuals()
        ga.crossover()
        ga.mutate()
        #probabilmente qua bisogna rinominare tutti gli individui con i nomi che avevano prima
        ga.resetNames(populationNames)


        jsonPopulation = parser.dumpPopulationToJson(ga.population)
        #print(jsonPopulation)
        com.sendPopulation(jsonPopulation)
        ngen += 1
