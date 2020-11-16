#!/bin/sh

for instance in 0 1 2 3 4 5 6
do
    ## EA
    # selection
    java -jar metah.jar $instance EA TOUR-5 OX INV 1000 1000 0.7 0.1 10
    java -jar metah.jar $instance EA TOUR-6 OX INV 1000 1000 0.7 0.1 10
    java -jar metah.jar $instance EA TOUR-7 OX INV 1000 1000 0.7 0.1 10
    java -jar metah.jar $instance EA ROULETTE OX INV 1000 1000 0.7 0.1 10

    # crossover
    java -jar metah.jar $instance EA TOUR-5 OX INV 1000 1000 0.5 0.1 10
    java -jar metah.jar $instance EA TOUR-5 OX INV 1000 1000 0.6 0.1 10
    java -jar metah.jar $instance EA TOUR-5 OX INV 1000 1000 0.7 0.1 10
    java -jar metah.jar $instance EA TOUR-5 PMX INV 1000 1000 0.5 0.1 10
    java -jar metah.jar $instance EA TOUR-5 PMX INV 1000 1000 0.6 0.1 10
    java -jar metah.jar $instance EA TOUR-5 PMX INV 1000 1000 0.7 0.1 10

    # mutation
    java -jar metah.jar $instance EA TOUR-5 OX INV 1000 1000 0.7 0.05 10
    java -jar metah.jar $instance EA TOUR-5 OX INV 1000 1000 0.7 0.1 10
    java -jar metah.jar $instance EA TOUR-5 OX INV 1000 1000 0.7 0.15 10
    java -jar metah.jar $instance EA TOUR-5 OX SWAP 1000 1000 0.7 0.05 10
    java -jar metah.jar $instance EA TOUR-5 OX SWAP 1000 1000 0.7 0.1 10
    java -jar metah.jar $instance EA TOUR-5 OX SWAP 1000 1000 0.7 0.15 10


    ## TS
    # nsize/tabu = 0.25
    java -jar metah.jar $instance TS 1000 5 INV 20 10
    java -jar metah.jar $instance TS 1000 10 INV 40 10
    java -jar metah.jar $instance TS 1000 25 INV 100 10
    java -jar metah.jar $instance TS 1000 50 INV 200 10

    # nsize/tabu = 0.5
    java -jar metah.jar $instance TS 1000 10 INV 20 10
    java -jar metah.jar $instance TS 1000 20 INV 40 10
    java -jar metah.jar $instance TS 1000 50 INV 100 10
    java -jar metah.jar $instance TS 1000 100 INV 200 10

    # nsize/tabu = 0.75
    java -jar metah.jar $instance TS 1000 15 INV 20 10
    java -jar metah.jar $instance TS 1000 30 INV 40 10
    java -jar metah.jar $instance TS 1000 75 INV 100 10
    java -jar metah.jar $instance TS 1000 150 INV 200 10

    # nsize/tabu = 1.0
    java -jar metah.jar $instance TS 1000 20 INV 20 10
    java -jar metah.jar $instance TS 1000 40 INV 40 10
    java -jar metah.jar $instance TS 1000 100 INV 100 10
    java -jar metah.jar $instance TS 1000 200 INV 200 10

    # swap
    java -jar metah.jar $instance TS 1000 50 SWAP 200 10
    java -jar metah.jar $instance TS 1000 100 SWAP 200 10
    java -jar metah.jar $instance TS 1000 150 SWAP 200 10
    java -jar metah.jar $instance TS 1000 200 SWAP 200 10

    ## SA
    # coolingF
    java -jar metah.jar $instance SA 10 1000 3 INV 200.0 1.0 0.99
    java -jar metah.jar $instance SA 10 1000 3 INV 200.0 1.0 0.98
    java -jar metah.jar $instance SA 10 1000 3 INV 200.0 1.0 0.95
    java -jar metah.jar $instance SA 10 1000 3 INV 200.0 1.0 0.93

    # start/end temp
    java -jar metah.jar $instance SA 10 1000 3 INV 500.0 20.0 0.98
    java -jar metah.jar $instance SA 10 1000 3 INV 200.0 10.0 0.98
    java -jar metah.jar $instance SA 10 1000 3 INV 100.0 5.0 0.98
    java -jar metah.jar $instance SA 10 1000 3 INV 50.0 2.0 0.98
    java -jar metah.jar $instance SA 10 1000 3 INV 20.0 1.0 0.98
    java -jar metah.jar $instance SA 10 1000 3 INV 200.0 1.0 0.98
    java -jar metah.jar $instance SA 10 1000 3 INV 100.0 1.0 0.98
    java -jar metah.jar $instance SA 10 1000 3 INV 50.0 1.0 0.98
    java -jar metah.jar $instance SA 10 1000 3 INV 100.0 0.1 0.98
    java -jar metah.jar $instance SA 10 1000 3 INV 50.0 0.1 0.98

    # swap
    java -jar metah.jar $instance SA 10 1000 3 SWAP 200.0 1.0 0.98
    java -jar metah.jar $instance SA 10 1000 3 SWAP 100.0 5.0 0.98
    java -jar metah.jar $instance SA 10 1000 3 SWAP 50.0 0.1 0.98
done