"""
Práctica 1 AEDAS Carlos García Santa, Joaquín Abad Díaz P03 G1218
"""
import numpy as np
from typing import Tuple

def matrix_multiplication(m_1: np.ndarray, m_2: np.ndarray) -> np.ndarray:
    """Multiply two matrices.

    Parameters:
    - m_1 (np.ndarray): First matrix.
    - m_2 (np.ndarray): Second matrix.

    Returns:
        np.ndarray: The result of multiplying m_1 by m_2.
    """
    if m_1.shape[1] != m_2.shape[0]:
        return -1

    m_3 = np.zeros((m_1.shape[0], m_2.shape[1]))

    for i in range(m_1.shape[0]):
        for j in range(m_2.shape[1]):
            for k in range(m_1.shape[1]):
                m_3[i][j] += m_1[i][k] * m_2[k][j]

    return m_3


def rec_bb(t, f, l, key):
    """
    Recursive binary search function to find a specified key in a sorted list.

    Parameters:
    - t (list[int or float]): The sorted list in which to search for the key.
    - f (int): The starting index of the search interval.
    - l (int): The ending index of the search interval.
    - key (int or float): The value to search for within the list.

    Returns:
    int or float or None:
        - If the key is found, it returns the key.
        - If the key is not found, it returns None
    """
    if l >= f:
        mid = (l + f) // 2
        if t[mid] == key:
            return t[mid]
        elif t[mid] < key:
            return rec_bb(t, mid + 1, l, key)
        else:
            return rec_bb(t, f, mid - 1, key)
    else:
        return None


def bb(t, f, l, key):
    """
    Binary search function to find a specified key in a sorted list.

    Parameters:
    - t (list[int or float]): The sorted list in which to search for the key.
    - f (int): The starting index of the search interval.
    - l (int): The ending index of the search interval.
    - key (int or float): The value to search for within the list.

    Returns:
    int or float or None:
        - If the key is found, it returns the key.
        - If the key is not found, it returns None.
    """
    while l >= f:
        mid = (l + f) // 2
        if t[mid] == key:
            return t[mid]
        elif t[mid] < key:
            f = mid + 1
        else:
            l = mid - 1
    return None


def min_heapify(h, i):
    """
    Maintains the min-heap property for a given list h.

    Parameters:
    - h (list[int or float]): The list representing the heap.
        - Assumes that the heap is represented as a zero-indexed list.
    - i (int): The index of the element in the heap that may be violating the min-heap property.
    """
    left = 2 * i + 1
    right = 2 * i + 2
    smallest = i

    if left < h.size and h[left] < h[smallest]:
        smallest = left

    if right < h.size and h[right] < h[smallest]:
        smallest = right

    if smallest != i:
        h[i], h[smallest] = h[smallest], h[i]
        min_heapify(h, smallest)


def insert_min_heap(h, k):
    """
    Insert an integer k into the min heap h.

    Parameters:
    - h: np.ndarray, the heap stored as a numpy array.
    - k: int, the integer to be inserted.

    Returns:
    - h: np.ndarray, the new heap with k inserted.
    """
    h = np.append(h, k)

    i = len(h) - 1

    while i > 0:
        parent = (i - 1) // 2

        if h[i] < h[parent]:
            h[i], h[parent] = h[parent], h[i]
            i = parent  
        else:
            break  
    return h


def create_min_heap(h):
    """
    Convert an arbitrary array h into a valid min heap in-place.

    Parameters:
    - h: np.ndarray, the array to be heapified.
    """
    for i in range(len(h) // 2 - 1, -1, -1):
        min_heapify(h, i)


def pq_ini():
    """
    Initializes an empty priority queue represented as a numpy array.

    Returns:
    - np.ndarray: An empty priority queue.
    """
    return np.array([], dtype=int)


def pq_insert(h, k):
    """
    Inserts an element into the min-heap.

    Parameters:
    - h (list): Input heap represented as a list.
    - k: Element to be inserted.

    Returns:
    - list: The modified heap after insertion.
    """
    return insert_min_heap(h, k)


def pq_remove(h):
    """
    Removes and returns the minimum value from the heap, and restructures the heap.

    Parameters:
    - h (list): Input heap represented as a list.

    Returns:
    - tuple: The minimum value and the modified heap.
    - ValueError: If the heap is empty.
    """
    if len(h) == 0:
        raise ValueError("Heap is empty.")

    min_val = h[0]
    h[0] = h[-1]
    h = h[:-1]

    min_heapify(h, 0)

    return min_val, h


def min_heap_sort(h: np.ndarray) -> np.ndarray:
    """
    Sorts an array using the min heap sort algorithm.

    Parameters:
    - h (np.ndarray): Input array to be sorted.

    Returns:
    - np.ndarray: Sorted array in ascending order.

    """
    create_min_heap(h)
    
    sorted_list = []
    n = len(h)
    for _ in range(n):
        h[0], h[-1] = h[-1], h[0]
        sorted_list.append(h[-1])
        h = h[:-1]
        
        min_heapify(h, 0)
    
    return np.array(sorted_list)
