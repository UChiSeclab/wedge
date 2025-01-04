A. Context:

You are an experienced C software engineer specializing in performance analysis, bug finding, and fuzzing-based testing. You will receive the following:

1. A problem statement describing the task or algorithm.
2. A C program that implements a solution to the problem described.
3. Two inputs: a fast input on which the program completes quickly, and slow input on which the program completes more slowly. These contrasting inputs are similar in size/structure but exhibit a significant difference in running time. The key idea behind this is to highlight subtle performance inefficiencies that aren’t always obvious with trivial min/max input sizes.
4. Line-level hit counts showing how often each line of code executes for both the slow and fast inputs. The key idea analyzing both fast and slow runs is to help pinpoint which parts of the code get “hit” many more times under the slow scenario.

B. Task

Your overall goal is to analyze and reason about why the program runs slowly for the slow input and then insert runtime checks into the original code to detect when similar slowdown conditions might occur. Ultimately, these checks will be used by a fuzzer to produce new test inputs that trigger the slow paths.

Based on the above information, you will work in phases:

Phase 1 -- Identify Expensive or Inefficient Code Segments. In this phase you need to compare line-level hit counts for the fast and slow runs. Next, pinpoint lines or functions that appear especially expensive or frequently executed under the slow input but less so under the fast input.

Phase 2 -- Derive Performance-Characterizing Invariants or Conditions. Based on these expensive or inefficient code segments, generate invariants or conditions describing when the code segment is likely to cause slow performance. Do not simply replicate the slow input’s exact values. Instead, propose general conditions (e.g., input array size exceeding a threshold, certain values in the input leading to repeated computations, etc.). For each invariant or condition generated, write a small code snippet that checks if the invariant or condition holds. This code snippet will be later integrated into the original program.

Phase 3 -- Insert Conditional Checks. Insert these conditions into the original C program as runtime checks, in the format described below. You should place these checks at appropriate program points, preferably near the start of the function or after reading inputs, rather than inside tight loops, so as not to add unnecessary overhead.
```
if (/* condition */) {
    cerr << "Warning: Potential performance bottleneck triggered!" << endl;
    abort();
}
```

C. Output Requirements

Provide the entire program, including your inserted performance-check conditions in code fences. For each inserted check, add a brief comment or short explanation so it’s clear what the check aims to detect (e.g., “// Check for large input size that triggers repeated iteration”). After the code, briefly summarize why these specific conditions were chosen and how they relate to the slow/fast contrast.


D. Important Considerations

1. Avoid hardcoding. Don’t rely solely on the exact values from the provided slow input; think in terms of categories or thresholds that lead to slow execution.
2. Avoid checks inside tight loops. Place checks in a way that does not significantly degrade performance.
3. Focus on fuzzer utility. The checks should help a fuzzer detect slow performance triggers by hitting these conditions.


E. In-context Examples

[in-context examples of invariants go here]


F. Problem Statement

[problem statement goes here]


G. Program Solving the Problem Statement

[code goes here]


H. The Fast and Slow Inputs

H.1. Fast Input

[input goes here]

H.2. Slow Input

[input goes here]


I. Line-level Hit Counts

I.1. Line-level Hit Counts for Fast Input:

[annotated code goes here]

I.2. Line-level Hit Counts for Slow Input:

[annotated code goes here]
