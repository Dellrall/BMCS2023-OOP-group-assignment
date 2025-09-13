#!/bin/bash
echo "Testing Vehicle Condition Selection and Auto ID Generation Logic..."

echo ""
echo "=== Condition Selection Tests ==="
test_condition() {
    input="$1"
    echo "Input: '$input'"
    
    conditionInput=$(echo "$input" | tr '[:upper:]' '[:lower:]')
    case $conditionInput in
        "a")
            echo "✅ Valid: Good"
            ;;
        "b") 
            echo "✅ Valid: Excellent"
            ;;
        "c")
            echo "✅ Valid: New"
            ;;
        *)
            echo "❌ Invalid condition. Please select a, b, or c."
            ;;
    esac
    echo ""
}

test_condition "a"
test_condition "b" 
test_condition "c"
test_condition "A"
test_condition "d"
test_condition "1"

echo "=== ID Generation Tests ==="
test_id() {
    type="$1"
    desc="$2"
    echo "Testing: $desc"
    
    case $type in
        1) prefix="MB"; maxId=20 ;;
        2) prefix="DB"; maxId=20 ;;
        3) prefix="BG"; maxId=15 ;;
        4) prefix="CR"; maxId=15 ;;
        *) prefix="V"; maxId=0 ;;
    esac
    
    generatedId="${prefix}$(printf "%03d" $((maxId + 1)))"
    echo "Generated ID: $generatedId"
    echo ""
}

test_id 1 "Mountain Bike"
test_id 2 "Dirt Bike" 
test_id 3 "Buggy"
test_id 4 "Crossover"

echo "Tests completed!"
