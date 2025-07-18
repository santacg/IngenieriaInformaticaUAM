# search.py
# ---------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
#
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).

Name student 1: Carlos García Santa
Name student 2: Joaquín Abad Díaz
IA lab group and pair: 1311 - 02

"""

import util
import pdb


class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def tinyMazeSearch(search_problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions

    s = Directions.SOUTH
    w = Directions.WEST
    return [s, s, w, s, w, w, s, w]

def depthFirstSearch(search_problem):
    """
    Search the deepest nodes in the search tree first.

    Your search algorithm needs to return a list of actions that reaches the
    goal. Make sure to implement a graph search algorithm.

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:
    """

    "*** YOUR CODE HERE ***"
    # Initialize a stack to manage visited nodes (LIFO for DFS)
    stack = util.Stack()
    visited_list = set()
    # The stack stores tuples of state and the path to the state
    stack.push((search_problem.getStartState(), []))  
    
    while not stack.isEmpty():
        state, actions = stack.pop()
        
        if search_problem.isGoalState(state):
            return actions  

        if state not in visited_list:
            visited_list.add(state) 
            for successor, action, _ in search_problem.getSuccessors(state):
                stack.push((successor, actions + [action]))

    return None



def breadthFirstSearch(search_problem):
    """Search the shallowest nodes in the search tree first."""
    "*** YOUR CODE HERE ***"
    # Initialize a queue to manage visited nodes (FIFO for BFS)
    queue = util.Queue()
    visited_list = set()
    queue.push((search_problem.getStartState(), []))

    while not queue.isEmpty():
        state, actions = queue.pop()

        if search_problem.isGoalState(state):
            return actions 

        if state not in visited_list:
            visited_list.add(state)
            for successor, action, _ in search_problem.getSuccessors(state):
                queue.push((successor, actions + [action]))

    return None


def uniformCostSearch(search_problem):
    """Search the node of least total cost first."""
    "*** YOUR CODE HERE ***"
    # Initialize a priority queue for visited nodes
    prio_queue = util.PriorityQueue()
    visited_list = set()
    # Push initial state, empty path, and 0 cost with priority 0
    prio_queue.push((search_problem.getStartState(), [], 0), 0)

    while not prio_queue.isEmpty():
        state, actions, cost = prio_queue.pop()
        
        if search_problem.isGoalState(state):
            return actions
        
        if state not in visited_list:
            visited_list.add(state)
            for successor, action, step_cost in search_problem.getSuccessors(state):
                # Calculate new cost
                new_cost = cost + step_cost
                # Push successor, updated path, and new cost with new cost as priority
                prio_queue.push((successor, actions + [action], new_cost), new_cost)

    return None


def nullHeuristic(state, search_problem=None):
    return 0


def aStarSearch(search_problem, heuristic=nullHeuristic):
    """Search the node that has the lowest combined cost and heuristic first."""
    "*** YOUR CODE HERE ***"
    prio_queue = util.PriorityQueue()
    start_state = search_problem.getStartState()
    # Push the starting state into the queue, with its cost function f = g + h, where g is 0
    prio_queue.push((start_state, [], 0), 0 + heuristic(start_state, search_problem))
    
    visited_list = set()
    
    while not prio_queue.isEmpty():
        # Pop state, path, and cost with the lowest total cost f from the priority queue
        state, actions, cost_g = prio_queue.pop()
        if search_problem.isGoalState(state):
            return actions
        
        if state not in visited_list:
            visited_list.add(state)  

            for successor, action, step_cost in search_problem.getSuccessors(state):
                new_cost_g = cost_g + step_cost
                # Calculate the heuristic cost h for successor.
                cost_h = heuristic(successor, search_problem)
                # Calculate total cost f = g + h for successor.
                cost_f = new_cost_g + cost_h
                # Push successor with its corresponding path and cost_g with priority equal to heuristic + cost of action.
                prio_queue.push((successor, actions + [action], new_cost_g), cost_f)
    
    return None



# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
