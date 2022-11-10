# Example 1

Write a predicate **twice(In, Out)** whose left argument is a list, and whose right argument is (also) a list but consisting of every element in the left list written twice. For example, the query: 

```
twice([a, 4, buggle], X).  
```

... should return: 

```
X = [a, a, 4, 4, buggle, buggle]).  
```

And the query: 

```
twice([1, 2, 1, 1], X).  
```

... should return: 

```
X = [1, 1, 2, 2, 1, 1, 1, 1] 
```
 
# Example 2

Suppose we are given a knowledge based with the following facts (pten translates number words from Portuguese to English):  

```
pten(um,one). 
pten(dois,two). 
pten(tres,three). 
pten(quatro,four). 
pten(cinco,five). 
pten(seis,six). 
pten(sete,seven). 
pten(oito,eight). 
pten(nove,nine). 
pten(dez,ten). 
```

Write a predicate **listpten** which translates a list of Portuguese number words to the corresponding list of English number words.  For example: 

```
listpen([tres, cinco, sete], [three, five, seven]).  
```

# Example 3

Write a predicate **factorial** that can be used to compute the factorial of a given number. Hint: fill in the blanks appropriately. 

```
factorial(0, ___).
factorial(X, Y) :- X > 0, X_dec is ___, factorial(___, Z), Y is ___.
```

# Example 4

Write a predicate **subs(X, Y, L1, L2)** having L2 as the result of substituting Y for all occurences of X in L1. For example, **subs(a, x, [a, b, a, c], [x, b, x, c])** is **true** whereas **subs(a, x, [a, b, a, c], [a, b, x, c])** is **false**. 