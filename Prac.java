import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
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
        if (n < 0)
            return;
        if (miniAns.size() == k) {
            if (n == 0) {
                List<Integer> list = new ArrayList<>(miniAns);
                ans.add(list);
            }
            return;
        }
        for (int j = start; j <= 9; j++) {
            miniAns.add(j);
            helper(ans, miniAns, j + 1, k, n - j);
            miniAns.remove(miniAns.size() - 1);
        }
    }

    // 299. Bulls and Cows by HashMap

    public String getHint(String secret, String guess) {
        String bulls = "";
        int cows = 0;
        HashMap<Character, Integer> map1 = new HashMap<>();
        HashMap<Character, Integer> map2 = new HashMap<>();

        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == guess.charAt(i)) {
                bulls += secret.charAt(i);
            } else {
                if (map1.containsKey(secret.charAt(i))) {
                    map1.put(secret.charAt(i), map1.get(secret.charAt(i)) + 1);
                } else {
                    map1.put(secret.charAt(i), 1);
                }
                if (map2.containsKey(guess.charAt(i))) {
                    map2.put(guess.charAt(i), map2.get(guess.charAt(i)) + 1);
                } else {
                    map2.put(guess.charAt(i), 1);
                }
            }
        }
        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == guess.charAt(i)) {
                continue;
            } else if (map2.containsKey(secret.charAt(i))) {
                cows++;
                if (map1.get(secret.charAt(i)) > 1) {
                    map1.put(secret.charAt(i), map1.get(secret.charAt(i)) - 1);
                } else {
                    map1.remove(secret.charAt(i));
                }
                if (map2.get(secret.charAt(i)) > 1) {
                    map2.put(secret.charAt(i), map2.get(secret.charAt(i)) - 1);
                } else {
                    map2.remove(secret.charAt(i));
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(bulls.length());
        sb.append("A");
        sb.append(String.valueOf(cows));
        sb.append("B");
        return sb.toString();
    }

    // 368. Largest Divisible Subsets

    List<Integer> res;
    int mem[];

    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);
        mem = new int[nums.length];
        Arrays.fill(mem, -1);
        res = new ArrayList<>();
        helper(nums, 0, new ArrayList<>(), 1);
        return res;
    }

    public void helper(int nums[], int start, List<Integer> curr, int prev) {
        if (curr.size() > res.size()) {
            res = new ArrayList<>(curr);
        }

        for (int i = start; i < nums.length; i++) {
            if (curr.size() > mem[i] && nums[i] % prev == 0) {
                mem[i] = curr.size();
                curr.add(nums[i]);
                helper(nums, i + 1, curr, nums[i]);
                curr.remove(curr.size() - 1);
            }
        }
    }

    // 396. Rotate Function

    public int maxRotateFunction(int[] nums) {
        int sum = 0, iteration = 0, max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            iteration += (i * nums[i]);
        }
        max = iteration;
        for (int i = nums.length - 1; i > 0; i--) {
            iteration += (sum - nums.length * (nums[i]));
            max = Math.max(max, iteration);
        }
        return max;
    }

    // 391. Perfect Rectangle

    public boolean isRectangleCover(int[][] rectangles) {
        int X1 = Integer.MAX_VALUE, Y1 = Integer.MAX_VALUE;
        int X2 = Integer.MIN_VALUE, Y2 = Integer.MIN_VALUE;
        int currSum = 0;
        Set<String> set = new HashSet<>();

        for (int[] point : rectangles) {
            int x1 = point[0], y1 = point[1], x2 = point[2], y2 = point[3];
            X1 = Math.min(X1, x1);
            Y1 = Math.min(Y1, y1);
            X2 = Math.max(X2, x2);
            Y2 = Math.max(Y2, y2);
            currSum += (x2 - x1) * (y2 - y1);
            String p1 = x1 + "," + y1;
            String p2 = x2 + "," + y2;
            String p3 = x1 + "," + y2;
            String p4 = x2 + "," + y1;

            String[] points = { p1, p2, p3, p4 };
            for (String p : points) {
                if (set.contains(p)) {
                    set.remove(p);
                } else {
                    set.add(p);
                }
            }
        }
        int sum = (X2 - X1) * (Y2 - Y1);
        if (currSum != sum)
            return false;
        if (set.size() != 4)
            return false;
        if (!set.contains(X1 + "," + Y1)) {
            return false;
        } else if (!set.contains(X2 + "," + Y2)) {
            return false;
        } else if (!set.contains(X1 + "," + Y2)) {
            return false;
        } else if (!set.contains(X2 + "," + Y1)) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {

    }
}