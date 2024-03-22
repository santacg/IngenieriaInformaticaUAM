"""The p303 module implements various algorithms including quick selection, quick sort,
dynamic programming for change calculation, and solutions for knapsack problem."""

from collections import OrderedDict
from typing import List, Tuple, Union, Dict
import numpy as np


def split(t: np.ndarray) -> Tuple[np.ndarray, int, np.ndarray]:
    """
    Splits an array into two sub-arrays based on a median value.

    Args:
        t: Numpy array to be split.

    Returns:
        Tuple[np.ndarray, int, np.ndarray]: A tuple containing two sub-arrays
        and the median value. The first sub-array contains elements less than the
        median, and the second sub-array contains elements greater than the median.
    """
    mid = t[0]
    t_l = [u for u in t if u < mid]
    t_r = [u for u in t if u > mid]

    return t_l, mid, t_r


def split_pivot(t: np.ndarray, mid: int) -> Tuple[np.ndarray, int, np.ndarray]:
    """
    Splits an array into two sub-arrays based on a given pivot value.

    Args:
        t: Numpy array to be split.
        mid: The pivot value for the split.

    Returns:
        Tuple[np.ndarray, int, np.ndarray]: A tuple containing two sub-arrays
        and the pivot value. The first sub-array has elements less than the pivot,
        while the second has elements greater.
    """
    t_l = [u for u in t if u < mid]
    t_r = [u for u in t if u > mid]

    return t_l, mid, t_r


def pivot5(t: np.ndarray) -> int:
    """
    Finds a suitable pivot for array division using the median of medians in groups of size 5.

    Args:
        t: Numpy array from which to find the pivot.

    Returns:
        int: The pivot value.
    """
    subArrays = np.array_split(t, max(1, len(t) // 5))
    median = [np.median(array) for array in subArrays]

    pivot = int(np.median(median))
    return pivot


def qsel(t: np.ndarray, k: int) -> Union[int, None]:
    """
    Quick selection recursively to find the k-th smallest element in an array.

    Args:
        t: Numpy array.
        k: Index of the element to find (0-based).

    Returns:
        Union[int, None]: The k-th smallest element in the array, or None if not found.
    """
    if len(t) == 1 and k == 0:
        return t[0]

    if k < 0 or k > len(t) - 1:
        return None

    t_l, mid, t_r = split(t)
    m = len(t_l)
    if k == m:
        return mid
    if k < m:
        return qsel(t_l, k)
    return qsel(t_r, k-m-1)


def qsel_nr(t: np.ndarray, k: int) -> Union[int, None]:
    """
    Quick selection non-recursively to find the k-th smallest element in an array.

    Args:
        t: Numpy array.
        k: Index of the element to find (0-based).

    Returns:
        Union[int, None]: The k-th smallest element in the array, or None if not found.
    """
    while len(t) > 1:
        if k < 0 or k > len(t) - 1:
            return None

        t_l, mid, t_r = split(t)
        m = len(t_l)
        if k == m:
            return mid
        if k < m:
            t = t_l
        else:
            k = k - m - 1
            t = t_r

    return t[0] if len(t) == 1 and k == 0 else None


def qsel5_nr(t: np.ndarray, k: int) -> Union[int, None]:
    """
    Quick selection non-recursively using 'pivot5' to find the k-th smallest element.

    Args:
        t: Numpy array.
        k: Index of the element to find (0-based).

    Returns:
        Union[int, None]: The k-th smallest element in the array, or None if not found.
    """
    while len(t) > 1:
        if k < 0 or k > len(t) - 1:
            return None

        t_l, mid, t_r = split_pivot(t, pivot5(t))
        m = len(t_l)
        if k == m:
            return mid
        if k < m:
            t = t_l
        else:
            k = k - m - 1
            t = t_r

    return t[0] if len(t) == 1 and k == 0 else None


def qsort_5(t: np.ndarray) -> np.ndarray:
    """
    Quick sort using 'pivot5' for dividing the array.

    Args:
        t: Numpy array to be sorted.

    Returns:
        np.ndarray: Sorted array.
    """
    if len(t) <= 1:
        return t

    t_l, m, t_r = split_pivot(t, pivot5(t))
    t_lSorted = qsort_5(t_l)
    t_rSorted = qsort_5(t_r)

    return t_lSorted + [m] + t_rSorted


def change_pd(c: int, l_coins: List[int]) -> np.ndarray:
    """
    Computes the minimum change for a given amount using dynamic programming.

    Args:
        c: Total amount for which to compute the change.
        l_coins: List of available coin denominations.

    Returns:
        np.ndarray: A matrix with the minimum number of coins needed for each sub-amount.
    """
    lcoin_len = len(l_coins)
    d_change = np.zeros((lcoin_len, c+1), dtype=int)

    for i in range(0, lcoin_len):
        for j in range(0, c+1):
            if i == 0:
                d_change[i, j] = j
            elif l_coins[i] > j:
                d_change[i, j] = d_change[i-1, j]
            else:
                d_change[i, j] = min(
                    d_change[i-1, j], 1 + d_change[i, j - l_coins[i]])

    return d_change


def optimal_change_pd(c: int, l_coins: List[int]) -> Dict:
    """
    Calculates the optimal way to make change for a given amount using dynamic programming.

    Args:
        c: Total amount for which to make change.
        l_coins: List of available coin denominations.

    Returns:
        Dict: A dictionary mapping each coin denomination to the number of times it is used.
    """
    change_dict = {}
    d_change = change_pd(c, l_coins)
    len_list = len(l_coins) - 1

    while c != 0:
        if d_change[len_list, c] == d_change[len_list - 1, c]:
            len_list -= 1
        else:
            coin = l_coins[len_list]
            change_dict[coin] = change_dict.get(coin, 0) + 1
            c -= coin

    ordered_change_dict = dict(OrderedDict(sorted(change_dict.items())))
    return ordered_change_dict


def knapsack_fract_greedy(l_weights: List[int], l_values: List[int], bound: int) -> Dict:
    """
    Implements a greedy algorithm for the fractional knapsack problem.

    Args:
        l_weights: List of weights of items.
        l_values: List of values of items.
        bound: The weight limit of the knapsack.

    Returns:
        Dict: A dictionary representing the amount of each item included in the knapsack.
    """
    rel_values = [(i, l_values[i] / l_weights[i])
                  for i in range(len(l_weights))]
    rel_values.sort(reverse=True)

    knapsack_dict = {i: 0 for i in range(len(l_weights))}
    total_weight = 0

    for rvalue in rel_values:
        index = rvalue[0]
        if total_weight + l_weights[index] <= bound:
            knapsack_dict[index] = l_weights[index]
            total_weight += l_weights[index]
        else:
            remain = bound - total_weight
            fraction = remain / l_weights[index]
            knapsack_dict[index] = l_weights[index] * fraction
            break

    return knapsack_dict


def knapsack_01_pd(l_weights: List[int], l_values: List[int], bound: int) -> int:
    """
    Solves the 0/1 knapsack problem using dynamic programming.

    Args:
        l_weights: List of weights of items.
        l_values: List of values of items.
        bound: The weight limit of the knapsack.

    Returns:
        int: The maximum value that can be achieved within the given weight limit.
    """
    len_list = len(l_weights)
    knapsack_pd = np.zeros((len_list, bound+1), dtype=int)

    for i in range(0, len_list):
        for j in range(0, bound+1):
            if i == 0:
                if l_weights[0] > j:
                    knapsack_pd[0, j] = 0
                else:
                    knapsack_pd[0, j] = l_values[0]
            elif l_weights[i] > j:
                knapsack_pd[i, j] = knapsack_pd[i - 1, j]
            else:
                knapsack_pd[i, j] = max(
                    knapsack_pd[i-1, j], knapsack_pd[i - 1, j - l_weights[i]] + l_values[i])

    return knapsack_pd[-1, -1]

