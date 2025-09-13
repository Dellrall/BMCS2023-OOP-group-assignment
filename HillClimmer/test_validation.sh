#!/bin/bash
echo "Testing Vehicle Type Validation Logic..."

test_input() {
    input="$1"
    echo ""
    echo "--- Testing: $2 ---"
    echo "Input: '$input'"
    
    if [ -z "$(echo "$input" | sed 's/ //g')" ]; then
        echo "❌ Please enter a valid selection."
    else
        if [[ "$input" =~ ^[0-9]+$ ]]; then
            type=$((input))
            if [ $type -lt 1 ] || [ $type -gt 4 ]; then
                echo "❌ Invalid vehicle type. Please select a number between 1 and 4."
            else
                echo "✅ Valid vehicle type selected: $type"
            fi
        else
            echo "❌ Invalid input. Please enter a number between 1 and 4."
        fi
    fi
}

# Test cases
test_input "abc" "Non-numeric input"
test_input "0" "Out of range (too low)"
test_input "5" "Out of range (too high)"
test_input "" "Empty input"
test_input " " "Whitespace only"
test_input "1" "Valid input - Mountain Bike"
test_input "2" "Valid input - Dirt Bike"
test_input "3" "Valid input - Buggy"
test_input "4" "Valid input - Crossover"

echo ""
echo "Vehicle type validation tests completed!"
