def generate_test_cases():
    max_length = 100000
    
    # Case 1: Long string of alternating 'ab'
    test_case1 = ("ab" * (max_length // 2))[:max_length]
    with open('./input/test_01.in', 'w') as f:
        f.write(test_case1 + "\n")
    
    # Case 2: Long string of 'a'
    test_case2 = "a" * max_length
    with open('./input/test_02.in', 'w') as f:
        f.write(test_case2 + "\n")
    
    # Case 3: Long string of 'b'
    test_case3 = "b" * max_length
    with open('./input/test_03.in', 'w') as f:
        f.write(test_case3 + "\n")

generate_test_cases()