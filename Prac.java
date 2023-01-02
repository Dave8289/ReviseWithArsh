import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Prac {
    // 150. Evaluate Reverse Polish Notation Using Stack

    public static int helper(int value1, int value2, String sign) {
        if (sign.equals("+"))
            return value1 + value2;
        if (sign.equals("*"))
            return value1 * value2;
        if (sign.equals("/"))
            return value1 / value2;
        return value1 - value2;
    }

    public static int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        String signs = "+-/*";
        for (String s : tokens) {
            if (signs.contains(s) && !stack.isEmpty()) {
                int a = stack.pop();
                int b = stack.pop();
                int result = helper(b, a, s);
                stack.push(result);
            } else {
                stack.push(Integer.parseInt(s));
            }
        }
        return stack.pop();
    }

    // 216. Combination Sum III Using BackTracking

    public static List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> ans = new ArrayList<>();
        helper(ans, new ArrayList<Integer>(), 1, k, n);
        return ans;
    }

    private static void helper(List<List<Integer>> ans, List<Integer> miniAns, int start, int k, int n) {
        if (miniAns.size() == k && n == 0) {
            List<Integer> list = new ArrayList<>(miniAns);
            ans.add(list);
            return;
        }
        for (int j = start; j <= 9; j++) {
            miniAns.add(j);
            helper(ans, miniAns, j + 1, k, n - j);
            miniAns.remove(miniAns.size() - 1);
        }
    }

    public static void main(String[] args) {
        int k = 3;
        int n = 9;
        System.out.println(combinationSum3(k, n));
    }
}