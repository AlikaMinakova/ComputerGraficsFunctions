package com.cgvsu.protocurvefxapp;

import java.util.*;
import java.util.function.Function;

public class ParserFunction {

    public static final HashMap<String, Integer> priority = new HashMap<>();

    static {
        priority.put("(", 0);
        priority.put(")", 0);
        priority.put("+", 1);
        priority.put("-", 1);
        priority.put("*", 2);
        priority.put("/", 2);
        priority.put("^", 3);
        priority.put("sqrt", 4);
        priority.put("log", 4);
        priority.put("sin", 4);
        priority.put("cos", 4);
        priority.put("tg", 4);
        priority.put("ctg", 4);
        priority.put("arcsin", 4);
        priority.put("arccos", 4);
        priority.put("arctg", 4);
        priority.put("arcctg", 4);
    }

    public static double actionTwoElem(double x1, double x2, String operator) {
        switch (operator) {
            case ("+"):
                return x1 + x2;
            case ("-"):
                return x1 - x2;
            case ("*"):
                return x1 * x2;
            case ("^"):
                return Math.pow(x1, x2);
            case ("/"):
                if (x2 != 0)
                    return x1 / x2;
                else throw new ArithmeticException();
        }
        throw new UndefinedOperator("оператор не определён");
    }

    public static double actionOneElem(double x1, String operator) {
        switch (operator) {
            case ("log"):
                return Math.log(x1);
            case ("sqrt"):
                return Math.sqrt(x1);
            case ("sin"):
                return Math.sin(x1);
            case ("cos"):
                return Math.cos(x1);
            case ("tg"):
                return Math.tan(x1);
            case ("ctg"):
                return 1.0 / Math.tan(x1);
            case ("arcsin"):
                return Math.asin(x1);
            case ("arccos"):
                return Math.acos(x1);
            case ("arctg"):
                return Math.atan(x1);
            case ("arcctg"):
                return Math.PI / 2 - Math.atan(x1);
        }
        throw new UndefinedOperator("оператор не определён");
    }

    public static double parser(String str, double x) {
        str = str.replaceAll(" ", "");
        if (x < 0) {
            str = str.replaceAll("x", " " + String.valueOf(x));
        } else {
            str = str.replaceAll("x", String.valueOf(x));
        }
        str = str.replaceAll("e", String.valueOf(Math.exp(1.0)));
        str = str.replaceAll("pi", String.valueOf(Math.PI));
        str = str.substring(2, str.length());

        Stack<Double> nums = new Stack<>();
        Stack<String> operations = new Stack<>();
        StringBuilder elem = new StringBuilder();
        char[] charStr = str.toCharArray();

        for (int i = 0; i < charStr.length; i++) {
            //если наткнулся на символ нужно проверить
            if (priority.containsKey(String.valueOf(charStr[i]))) {
                if (elem.length() != 0) {
                    if (priority.containsKey(String.valueOf(elem))) {
                        operations.push(String.valueOf(elem));
                    } else {
                        nums.push(Double.valueOf(String.valueOf(elem)));
                    }
                    elem = new StringBuilder();
                }
            }
            if (charStr[i] == '(') {
                operations.push(String.valueOf((charStr[i])));
            }
            if (charStr[i] == '^') {
                if (operations.size() > 0) {
                    if (priority.get(operations.peek()) > 3) {
                        double num1 = nums.pop();
                        String znak = operations.pop();
                        nums.push(actionOneElem(num1, znak));
                    } else if (priority.get(operations.peek()) == priority.get(String.valueOf(charStr[i]))) {
                        double num2 = nums.pop();
                        double num1 = nums.pop();
                        String znak = operations.pop();
                        nums.push(actionTwoElem(num1, num2, znak));
                    }
                    operations.push(String.valueOf(charStr[i]));

                } else {
                    operations.push(String.valueOf(charStr[i]));
                }
            }
            if (charStr[i] == '+' || charStr[i] == '-') {
                if (operations.size() > 0) {
                    if (priority.get(operations.peek()) > 3) {
                        double num1 = nums.pop();
                        String znak = operations.pop();
                        nums.push(actionOneElem(num1, znak));
                    } else if (priority.get(operations.peek()) >= priority.get(String.valueOf(charStr[i]))) {
                        double num2 = nums.pop();
                        double num1 = nums.pop();
                        String znak = operations.pop();
                        nums.push(actionTwoElem(num1, num2, znak));
                    }
                    operations.push(String.valueOf(charStr[i]));

                } else {
                    operations.push(String.valueOf(charStr[i]));
                }
            }
            if (charStr[i] == '*' || charStr[i] == '/') {
                if (operations.size() > 0) {
                    if (priority.get(operations.peek()) > 3) {
                        double num1 = nums.pop();
                        String znak = operations.pop();
                        nums.push(actionOneElem(num1, znak));
                    } else if (priority.get(operations.peek()) >= priority.get(String.valueOf(charStr[i]))) {
                        double num2 = nums.pop();
                        double num1 = nums.pop();
                        String znak = operations.pop();
                        nums.push(actionTwoElem(num1, num2, znak));
                    }
                    operations.push(String.valueOf(charStr[i]));

                } else {
                    operations.push(String.valueOf(charStr[i]));
                }
            }
            if (charStr[i] == ')') {
                while (!Objects.equals(operations.peek(), "(")) {
                    if (priority.get(operations.peek()) > 3) {
                        double num1 = nums.pop();
                        String znak = operations.pop();
                        nums.push(actionOneElem(num1, znak));
                    } else {
                        double num2 = nums.pop();
                        double num1 = nums.pop();
                        String znak = operations.pop();
                        nums.push(actionTwoElem(num1, num2, znak));
                    }
                }
                operations.pop();
            }
            if (charStr[i] == ' ') {
                elem.append(charStr[i + 1]);
                i++;
            } else if (!priority.containsKey(String.valueOf(charStr[i]))) {
                elem.append(charStr[i]);
            }
        }
        if (elem.length() > 0) {
            nums.push(Double.valueOf(String.valueOf(elem)));
            elem = new StringBuilder();
        }
        while (operations.size() != 0) {
            if (priority.get(operations.peek()) > 3) {
                nums.push(actionOneElem(nums.pop(), operations.pop()));
            } else {
                double num = nums.pop();
                nums.push(actionTwoElem(nums.pop(), num, operations.pop()));
            }
        }
        if (nums.size() > 0) {
            return nums.peek();
        }
        return 0;
    }

}
//y=sin(4*x) / sin(5 * x)
//y=1/x*sin(x)*50