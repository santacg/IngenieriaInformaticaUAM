"""Module for graph operations including Kruskal's algorithm and TSP algorithims implementations"""
import queue
from queue import PriorityQueue
from typing import List, Tuple
import random
import time
import itertools
import numpy as np


def init_cd(n: int) -> np.ndarray:
    """
    Initialize an array with all elements set to -1.

    Parameters:
    - n (int): The size of the array.

    Returns:
        np.ndarray: An array of size 'n' with all elements initialized to -1.
    """
    # use numpy function to fill array with -1 and n elements
    return np.full(n, -1)


def find(ind: int, p_cd: np.ndarray) -> int:
    """
    Find the representative element of a set in a disjoint-set data structure.

    Parameters:
    - ind (int): The index of the element to find its representative.
    - p_cd (np.ndarray): The parent array of the disjoint-set structure.

    Returns:
        int: The index of the representative of the set that 'ind' belongs to.
    """
    if p_cd[ind] < 0:
        return ind
    p_cd[ind] = find(p_cd[ind], p_cd)
    return p_cd[ind]


def union(rep_1: int, rep_2: int, p_cd: np.ndarray) -> int:
    """
    Perform the union operation on two sets in a disjoint-set data structure.

    Parameters:
    - rep_1 (int): The representative of the first set.
    - rep_2 (int): The representative of the second set.
    - p_cd (np.ndarray): The parent array of the disjoint-set structure.

    Returns:
        int: The representative of the merged set.
    """
    rep_1 = find(rep_1, p_cd)
    rep_2 = find(rep_2, p_cd)

    if rep_1 == rep_2:
        return rep_1

    # Union by rank
    if -p_cd[rep_1] < -p_cd[rep_2]:  # if rep1 rank is less than rep_2
        p_cd[rep_1] = rep_2
    elif -p_cd[rep_1] > -p_cd[rep_2]:  # if rep1 rank is bigger than rep_2
        p_cd[rep_2] = rep_1
    else:
        p_cd[rep_2] = rep_1
        p_cd[rep_1] -= 1  # Increment rank if equal

    return find(rep_1, p_cd)  # Return rep


def create_pq(n: int, l_g: List) -> queue.PriorityQueue:
    """
    Create a priority queue from a list of edges.

    Parameters:
    - n (int): The number of vertices.
    - l_g (List): A list of edges where each edge 
    is represented as a tuple (vertex1, vertex2, weight).

    Returns:
        queue.PriorityQueue: A priority queue containing the edges, ordered by weight.
    """
    pq = PriorityQueue()

    if n <= 1:
        return pq  # Return the empty queue if there are 1 or fewer vertices

    # Loop through all edges and add them to the priority queue
    for edge in l_g:
        u, v, w = edge
        # Put the edge in the queue with weight as the priority
        pq.put((w, (u, v)))
    return pq


def kruskal(n: int, l_g: List) -> Tuple[int, List]:
    """
    Implement Kruskal's algorithm to find the Minimum Spanning Tree (MST) of a graph.

    Parameters:
    - n (int): The number of vertices in the graph.
    - l_g (List): A list of edges in the graph,
    where each edge is a tuple (vertex1, vertex2, weight).

    Returns:
        Tuple[int, List]: A tuple containing the number of vertices of the MST and a list of edges in the MST.
    """
    ds = init_cd(n)  # Initialize the disjoint set
    pq = create_pq(n, l_g)  # Create a priority queue from the list of edges
    l_t = []  # List to store the edges of the Minimum Spanning Tree (MST)

    # Process the queue until it's empty
    while not pq.empty():
        w, item = pq.get()  # Get the edge with the lowest weight
        u, v = item
        u_rep = find(u, ds)  # Find the representative of u
        v_rep = find(v, ds)  # Find the representative of v

        # If u and v are in different sets, add the edge to the MST
        if u_rep != v_rep:
            l_t.append((u, v, w))
            union(u_rep, v_rep, ds)  # Merge the sets of u and v

    # Return the MST if it has n-1 edges
    if len(l_t) == n - 1:
        return (n, l_t)

    return None  # Return None if MST is not found


def kruskal2(n: int, l_g: List) -> Tuple[int, List, float]:
    """
    Repeatedly executes the greedy TSP algorithm, starting from each city.

    Parameters:
    - dist_m (np.ndarray): Distance matrix between cities.

    Returns:
        List[int]: The circuit with the shortest length found.
    """
    ds = init_cd(n)
    pq = create_pq(n, l_g)
    l_t = []
    disjoint_set_time = 0  # Variable to track time spent on disjoint set operations

    while not pq.empty():
        w, item = pq.get()
        u, v = item
        start_time = time.time()  # Start timing
        u_rep = find(u, ds)
        v_rep = find(v, ds)
        disjoint_set_time += time.time() - start_time  # Add time spent on find operation

        if u_rep != v_rep:
            start_time = time.time()  # Start timing
            union(u_rep, v_rep, ds)
            # Add time spent on union operation
            disjoint_set_time += time.time() - start_time
            l_t.append((u, v, w))

    # Return the MST and the time spent if the MST is complete
    if len(l_t) == n - 1:
        return (n, l_t, disjoint_set_time)

    return None


def complete_graph(n_nodes: int, max_weight=50) -> Tuple[int, List]:
    """
    Generates a complete graph with a given number of nodes and a max weight which is the limit random value that a weight can have.

    Parameters:
    - n_nodes (int): The number of nodes in the graph.
    - max_weight (int): Max random weight.

    Returns:
        Tuple[int, List]: A tuple containing the number of nodes of the graph and a list of the weights and pair of connected vertices in the graph.
    """
    l_g = []
    # Loop through all pairs of nodes to generate edges
    for u in range(n_nodes):
        for v in range(u + 1, n_nodes):
            w = random.randint(1, max_weight)
            l_g.append((u, v, w))
    return n_nodes, l_g  # Return the number of nodes and the list of edges


def time_kruskal(n_graphs: int, n_nodes_ini: int, n_nodes_fin: int, step: int) -> List:
    """
    Measures the execution time of Kruskal's algorithm for various graph sizes.

    Parameters:
    - n_graphs (int): Number of graphs to generate for each size.
    - n_nodes_ini (int): Initial number of nodes in the graphs.
    - n_nodes_fin (int): Final number of nodes in the graphs.
    - step (int): Increment in the number of nodes between consecutive graphs.

    Returns:
        List: A list of execution times for each graph size.
    """
    times = []

    # Loop over a range of graph sizes
    for n_nodes in range(n_nodes_ini, n_nodes_fin + 1, step):
        graph_times = []
        # Generate and process multiple graphs for each size
        for _ in range(n_graphs):
            n_nodes, l_g = complete_graph(n_nodes)
            clk_start = time.time()  # Start time
            kruskal(n_nodes, l_g)
            clk_stop = time.time()  # Stop time
            # Calculate elapsed time
            graph_times.append((clk_stop - clk_start))

        # Append the average time for this graph size
        times.append((n_nodes, sum(graph_times) / n_graphs))

    return times  # Return the average times for each graph size


def time_kruskal_2(
    n_graphs: int, n_nodes_ini: int, n_nodes_fin: int, step: int
) -> List:
    """
    Measures the execution time of an alternative version of Kruskal's algorithm for various graph sizes.

    Parameters:
    - n_graphs (int): Number of graphs to generate for each size.
    - n_nodes_ini (int): Initial number of nodes in the graphs.
    - n_nodes_fin (int): Final number of nodes in the graphs.
    - step (int): Increment in the number of nodes between consecutive graphs.

    Returns:
        List: A list of execution times for each graph size.
    """
    times = []
    # Iterate over a range of graph sizes
    for n_nodes in range(n_nodes_ini, n_nodes_fin + 1, step):
        graph_times = []

        for _ in range(n_graphs):
            n_nodes, l_g = complete_graph(n_nodes)
            # Run Kruskal's algorithm and get time
            _, _, time = kruskal2(n_nodes, l_g)
            graph_times.append(time)  # Collect execution time

        times.append((n_nodes, sum(graph_times) / n_graphs))

    return times  # Return the average times for each graph size


def dist_matrix(n_nodes: int, weight_max=10) -> np.ndarray:
    """
    Generates a symmetric matrix representing random distances between nodes.

    The diagonal elements are set to 0 as the distance from a node to itself is zero.

    Parameters:
    - n_nodes (int): The number of nodes (cities).
    - w_max (int, optional): The maximum weight (distance) between two nodes. Defaults to 10.

    Returns:
        np.ndarray: A symmetric matrix of distances between nodes.
    """
    # Create a random matrix with weights between 1 and w_max inclusive
    m = np.random.randint(1, weight_max + 1, (n_nodes, n_nodes))
    # Make the matrix symmetric by averaging it with its transpose
    m = (m + m.T) // 2
    # Set the diagonal to 0, as the distance from a node to itself is zero
    np.fill_diagonal(m, 0)
    return m


def greedy_tsp(dist_m: np.ndarray, node_ini=0) -> List:
    """
    Constructs a circuit for the Traveling Salesman Problem using a greedy approach.

    This function selects the nearest unvisited city at each step.

    Parameters:
    - dist_m (np.ndarray): A square matrix where dist_m[i, j] is the distance from city i to city j.
    - node_ini (int): Initial city to start the circuit.

    Returns:
        List: An array representing the order of cities visited in the circuit.
    """
    # Get the number of cities from the dimensions of the distance matrix
    num_cities = dist_m.shape[0]

    # Start the circuit with the specified starting city
    circuit = [node_ini]

    # Continue until all cities are included in the circuit
    while len(circuit) < num_cities:
        # Get the last visited city in the circuit
        current_city = circuit[-1]

        # Sort the remaining cities based on their distance to the current city.
        # np.argsort returns the indices of the cities sorted by distance.
        options = np.argsort(dist_m[current_city])

        # Iterate over the sorted cities to find the nearest unvisited one
        for city in options:
            # Check if the city is already in the circuit
            if city not in circuit:
                # If the city is not in the circuit, add it and break out of the loop
                circuit.append(city)
                break

    # At the end, return to the initial city to complete the circuit
    return circuit + [node_ini]


def repeated_greedy_tsp(dist_m: np.ndarray) -> List:
    """
    Repeatedly executes the greedy TSP algorithm, starting from each city.

    Parameters:
    - dist_m (np.ndarray): Distance matrix between cities.

    Returns:
        List: The circuit with the shortest length found.
    """
    num_cities = dist_m.shape[0]
    best_circuit = None  # Initialize the best circuit found
    best_length = float("inf")

    # Iterate over all cities to start the greedy circuit
    for start_city in range(num_cities):
        # Generate a greedy circuit from each city
        circuit = greedy_tsp(dist_m, start_city)
        circuit_length = sum(  # Calculate the length of the circuit
            dist_m[circuit[i], circuit[i + 1]] for i in range(len(circuit) - 1)
        )

        # Update the best circuit and length if a shorter one is found
        if circuit_length < best_length:
            best_length = circuit_length
            best_circuit = circuit

    return best_circuit  # Return the shortest circuit found


def exhaustive_tsp(dist_m: np.ndarray) -> List:
    """
    Solves the TSP exhaustively by testing all possible permutations.

    Parameters:
    - dist_m (np.ndarray): Distance matrix between cities.

    Returns:
        List: The shortest possible circuit.
    """
    num_cities = dist_m.shape[0]
    best_circuit = None
    best_length = float("inf")

    # Iterating over all permutations of the cities
    for circuit in itertools.permutations(range(num_cities)):
        # Add the start city at the end to complete the loop
        circuit = circuit + (circuit[0],)
        circuit_length = sum(
            dist_m[circuit[i], circuit[i + 1]] for i in range(len(circuit) - 1)
        )

        # Update the best circuit if this one is shorter
        if circuit_length < best_length:
            best_length = circuit_length
            best_circuit = circuit

    return list(best_circuit)


def len_circuit(circuit: List, dist_m: np.ndarray) -> int:
    """
    Calculates the total length of a given circuit in the TSP.

    Parameters:
    - circuit (List): List of cities in the order of the circuit.
    - dist_m (np.ndarray): Distance matrix between cities.

    Returns:
        int: The total length of the circuit.
    """
    total_length = 0
    # Iterate over pairs of consecutive cities
    for i in range(len(circuit) - 1):
        # Add distance between them
        total_length += dist_m[circuit[i], circuit[i + 1]]
    return total_length
