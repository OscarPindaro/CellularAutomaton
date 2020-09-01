import operator, random
from deap import algorithms, base, creator, tools, gp
from multiprocessing import Pool
import numpy as np

MAX_HEIGHT = 30

def evaluateIndividual(individual):
    return random.random()+1,

creator.create("FitnessMax", base.Fitness, weights=(1.0,))
creator.create("DecisionFunction", gp.PrimitiveTree)
creator.create("Individual", list, fitness=creator.FitnessMax)

# creates a primitive set with multiplication, addition and subtraction
pset = gp.PrimitiveSet(name="decisionTree", arity=1)
pset.addPrimitive(operator.add, arity=2)
pset.addPrimitive(operator.sub, arity=2)
pset.addPrimitive(operator.mul, arity=2)
pset.addEphemeralConstant("const", lambda: random.uniform(-1, 1))
#pset.renameArguments(ARG0="x")

toolbox = base.Toolbox()
#registering in order to enaple multiprocessing
pool = Pool()
toolbox.register("map", pool.map)
#registering the function that create trees, individuals and a population
toolbox.register("expr", gp.genFull, pset=pset, min_=1, max_=3)
toolbox.register("tree", tools.initIterate, creator.DecisionFunction, toolbox.expr)
toolbox.register("trees", tools.initRepeat, list, toolbox.tree, n = 5)
toolbox.register("individual", tools.initIterate, creator.Individual, toolbox.trees)
toolbox.register("population", tools.initRepeat, list, toolbox.individual)
#function used in the GA
toolbox.register("evaluate", evaluateIndividual)
toolbox.register("mate", gp.cxOnePoint)
toolbox.register("expr_mut", gp.genFull, pset=pset, min_=0,max_= 2)
toolbox.register("mutate", gp.mutUniform, expr=toolbox.expr_mut, pset=pset)
toolbox.register("select", tools.selTournament, tournsize=4)
#fix tree height
toolbox.decorate("mate", gp.staticLimit(key=operator.attrgetter("height"), max_value=MAX_HEIGHT))
toolbox.decorate("mutate", gp.staticLimit(key=operator.attrgetter("height"), max_value=MAX_HEIGHT))
#statistics module
stats = tools.Statistics(key=operator.attrgetter("fitness.values"))
stats.register("max", np.max)
stats.register("mean", np.mean)
stats.register("min", np.min)

if __name__ == "__main__":
    pop = toolbox.population(n=50)

    ngen, cxpb, mutpb = 10, 0.8, 0.1
    fitnesses = toolbox.map(toolbox.evaluate, pop)
    for (ind, fit) in zip(pop, fitnesses):
        ind.fitness.values = fit

    for j in range(ngen):
        pop = toolbox.select(pop, k=len(pop))
        pop = [toolbox.clone(ind) for ind in pop]

        for child1, child2 in zip(pop[::2], pop[1::2]):
            if random.random() < cxpb:
                print("CROSSOVER")
                tree1 = random.choice(child1)
                index1 = child1.index(tree1)
                tree2 = random.choice(child2)
                index2 = child2.index(tree2)
                print("BEFORE= {}\n{}".format(tree1,tree2))
                # newTree1, newTree2 = toolbox.mate(tree1, tree2)
                newTree1, newTree2 = gp.cxOnePoint(tree1, tree2)
                print("AFTER= {}\n{}\n\n".format(newTree1, newTree2))
                child1[index1] = newTree1
                child2[index2] = newTree2
                del child1.fitness.values, child2.fitness.values

        for mutant in pop:
            if random.random() < mutpb:
                tree = random.choice(mutant)

                index = mutant.index(tree)
                toolbox.mutate(tree)
                mutant[index] = tree
                del mutant.fitness.values

        invalids = [ind for ind in pop if not ind.fitness.valid]
        print(len(pop))
        fitnesses = toolbox.map(toolbox.evaluate, invalids)
        for ind, fit in zip(invalids, fitnesses):
            ind.fitness.values = fit
