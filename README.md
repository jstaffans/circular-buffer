circular-buffer
===============

Solutions for a programming challenge. 

```
Implement a circular buffer of size N. Allow the caller to append, remove and list 
the contents of the buffer. Implement the buffer to achieve maximum performance 
for each of the operations.

The new items are appended to the end and the order is retained i.e elements are 
placed in increasing order of their insertion time. When the number of elements in 
the list elements exceeds the defined size, the older elements are overwritten.
 
There are four types of commands.
 
"a"  n - Append the following n lines to the buffer. If the buffer is full they 
         replace the older entries.
"r"  n - Remove first n elements of the buffer. These n elements are the ones 
         that were added earliest among the current elements.
"l"    - List the elements of buffer in order of their inserting time.
"q"    - Quit
 
Input format:
 
First line of input contains N, the size of the buffer.
a n    - append the following n lines to the buffer.
r n    - remove first n elements of the buffer.
l      - list the buffer.
q      - Quit.
 
Output format:
 
Whenever l command appears in the input, print the elements of buffer in order of 
their inserting time. Element that was added first should appear first. 

```
