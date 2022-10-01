# Left Recursion Elimination

Eliminate any left-recursive productions (including indirect ones). When you are done, submit this README.md file with your answers on Canvas. 

By: Samuel Vang
## Q1

```
X -> XYz | Xw | w
Y -> Yp | q
```
```
X-> wX'
X'-> YzX' | wX' | ep
Y-> qY'
Y'-> pY' | ep
```

## Q2

```
S -> aA | Sd
A -> b
```
```
S -> abS'
S' -> dS'
```
## Q3

```
A -> Bxy | x
B -> CD
C -> A | c
D -> d           
```
```
A -> Bxy | x
B -> xDB' | cDB'
B' -> xyDB' | ep
D -> d
```
