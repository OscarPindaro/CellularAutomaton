import operator, random
from deap import algorithms, base, creator, tools, gp
from multiprocessing import Pool
import numpy as np
import json
import communication
import sys

class JsonParser:

    def __init__(self):
        pass

    def parsePopulation(self, data,pset):
        jsonData = json.loads(data)
        population = []

        for prey in data:
            listOfTrees = []
            for tree in prey["functions"]:
                listOfTrees.append(gp.PrimitiveTree.from_string(tree, pset))
            individual = creator.Individual(listOfTrees)
            print("controllo che prey sia solo una stringa e non altro {}".format(prey))
            individual.name = prey
            population.append(individual)
        return population

    #a dictionary where the key is the name of an individual and the value is its fitness
    def parseFitness(self, data):
        fitnesses = json.loads(data)
        return fitness

    #returns a disctionary
    def parseInputParams(self, data):
        return json.loads(data)

    def treeToString(self, trees):
        bigList = []
        for list in trees:
            individual = []
            for tree in list:
                individual.append(str(tree))
            bigList.append(individual)
        return json.dumps(bigList)


# communication = communication.Communication(12346)
# a = communication.readParameters()
# print(a)
# exit()


def evaluateIndividual(individual, fitnessDictionary):
    return fitnessDictionary[individual.name],


class GeneticAlgorithm:
    MAX_HEIGHT = 30

    def __init__(self, cxpb, mutpb):
        self.toolbox = base.Toolbox()
        self.pset = None
        #probably they will be a dictionary
        self.population = None
        self.fitness = None
        self.cxpb = cxpb
        self.mutpb = mutpb
        self.stats = None
        self.fitnessDictionary = {}

    def createPrimitiveSet(self, nOfArguments):
        self.pset = gp.PrimitiveSet(name="decisionTree", arity=nOfArguments)
        self.pset.addPrimitive(operator.add, arity=2)
        self.pset.addPrimitive(operator.sub, arity=2)
        self.pset.addPrimitive(operator.mul, arity=2)
        self.pset.addEphemeralConstant("const", lambda: random.uniform(-1, 1))

    def setCreator(self):
        creator.create("FitnessMax", base.Fitness, weights=(1.0,))
        creator.create("DecisionFunction", gp.PrimitiveTree)
        creator.create("Individual", list, fitness=creator.FitnessMax, name=None )

    def enableMultiprocessing(self):
        pool = Pool()
        self.toolbox.register("map", pool.map)

    def registerGeneratorFunctions(self, nOfFunctions, minHeight=1, maxHeight = 3):
        self.toolbox.register("expr", gp.genFull, pset=self.pset, min_=minHeight, max_=maxHeight)
        self.toolbox.register("tree", tools.initIterate, creator.DecisionFunction, self.toolbox.expr)
        self.toolbox.register("trees", tools.initRepeat, list, self.toolbox.tree, n = nOfFunctions)
        self.toolbox.register("individual", tools.initIterate, creator.Individual, self.toolbox.trees)
        self.toolbox.register("population", tools.initRepeat, list, self.toolbox.individual)

    def registerEvolutionFunctions(self, sizeTournament = 4, minGen=0, maxGen=2):
        self.toolbox.register("evaluate", evaluateIndividual, fitnessDictionary = this.fitnessDictionary)
        self.toolbox.register("mate", gp.cxOnePoint)
        self.toolbox.register("expr_mut", gp.genFull, pset=self.pset, min_=minGen,max_= maxGen)
        self.toolbox.register("mutate", gp.mutUniform, expr=self.toolbox.expr_mut, pset=self.pset)
        self.toolbox.register("select", tools.selTournament, tournsize=sizeTournament)
        #fix tree height
        self.toolbox.decorate("mate", gp.staticLimit(key=operator.attrgetter("height"), max_value=self.MAX_HEIGHT))
        self.toolbox.decorate("mutate", gp.staticLimit(key=operator.attrgetter("height"), max_value=self.MAX_HEIGHT))

    def setStatistics(self):
        stats = tools.Statistics(key=operator.attrgetter("fitness.values"))
        stats.register("max", np.max)
        stats.register("mean", np.mean)
        stats.register("min", np.min)

    def setPopulation(self, population):
        self.population = population

    def createRandomPopulation(self, size):
        self.population = self.toolbox.population(size)

    def computeFitness(self):
        fitnesses = self.toolbox.map(self.toolbox.evaluate, self.population)
        for (ind, fit) in zip(self.population, fitnesses):
            ind.fitness.values = fit

    def selectIndividuals(self):
        self.population = self.toolbox.select(self.population, k=len(self.population))
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
        for individual, name in zip(population, names):
            population.name = name


# parser = JsonParser()
# b ='[["mul(sub(ARG0, -0.8132880758124059), mul(ARG0, 0.26388341900976764))","mul(sub(ARG0, -0.8132880758124059), mul(ARG0, 0.26388341900976764))","mul(sub(ARG0, -0.8132880758124059), mul(ARG0, 0.26388341900976764))","mul(sub(ARG0, -0.8132880758124059), mul(ARG0, 0.26388341900976764))"]]'
# a =parser.parsePopulation(b,pset)
# b = parser.parseFitness('[1.0,2.0,3.0]')
# print(b[0]+1)
# exit()


if __name__ == "__main__":
    com= Communication(int(sys.argv[0]))
    parser = JsonParser()
    params = comm.readParameters()
    ga = GeneticAlgorithm(params["cxpb"], params["mutpb"])
    ga.setCreator()
    ga.createPrimitiveSet(params["numverOfInputs"])
    ga.enableMultiprocessing()
    ga.registerGeneratorFunctions(params["nOfFunctions"], minHeight=1, maxHeight = 3)
    ga.registerEvolutionFunctions()
    ga.setStatistics()

    populationJson = com.readPopulation()
    population = parser.parsePopulation(population, ga.pset)
    populationNames = [ el.keys()[0] for el in populationJson]
    print("Population names\n{}\n".format(populationNames))
    ga.setPopulation(population)
    ngen = 0
    stop = False

    while(!stop):
        jsonFitness = communication.readFitness()
        fitness = parser.parseFitness(jsonFitness)

        ga.fitnessDictionary = fitness
        ga.computeFitness()
        ga.selectIndividuals()
        ga.crossover()
        ga.mutate()
        #probabilmente qua bisogna rinominare tutti gli individui con i nomi che avevano prima
        ga.resetNames(populationNames)

        #inviare a java la nuova popolazione
        #jsonparser trasforma una lista di individui in un json
        #communication invia a java, immagino inviando "newPolulation" e il json
        ngen += 1
