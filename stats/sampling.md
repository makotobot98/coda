# Concepts

## Reservoir Sampling

**Intuition**

In order to do random sampling over a population of **unknown size** with **constant space**, the answer is [reservoir sampling](https://en.wikipedia.org/wiki/Reservoir_sampling). As one will see later, it is an elegant algorithm that can address the two caveats of the previous approach.

> The reservoir sampling algorithm is intended to sample `k` elements from an population of unknown size. In our case, the `k` happens to be one. 

- The output probability of each element in the sample is $$\frac{k}{n}$$

Furthermore, the reservoir sampling is a **family** of algorithms which includes several variants over the time. Here we present a simple albeit slow one, also known as ***Algorithm R*** by [Alan Waterman](https://en.wikipedia.org/wiki/Reservoir_sampling#cite_note-vitter-1).

```python
# S has items to sample, R will contain the result
def ReservoirSample(S[1..n], R[1..k])
  # fill the reservoir array
  for i := 1 to k
      R[i] := S[i]

  # replace elements with gradually decreasing probability
  for i := k+1 to n
    # randomInteger(a, b) generates a uniform integer
    #   from the inclusive range {a, ..., b} *)
    j := randomInteger(1, i)
    if j <= k
        R[j] := S[i]
```

### Questions

- **382. Linked List Random Node**