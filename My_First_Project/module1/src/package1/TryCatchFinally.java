package package1;

public class TryCatchFinally {
    public static void main(String[] args) {
//        如果不用异常处理机制,存在异常会导致系统崩溃,无法运行程序,有了异常处理机制,出现异常时执行catch中的代码块,不影响接下来的程序运行
        System.out.println("=========================异常处理机制===========================");

        /**
         在Java中，return 关键字在 try-catch 语句中的作用与其在其他地方的作用基本相同，但需要注意一些特殊情况和注意事项。

         正常情况下的 return：
         在正常情况下，return 用于从方法中返回一个值，并终止方法的执行。无论是在 try-catch 语句中还是在其他地方，return 都可以用于返回方法的结果。

         在 try 块内的 return：
         如果 return 语句位于 try 块内部，并且在 try 块中的代码执行之前就执行了 return，那么在执行 return 之前，会执行 finally 块中的代码（如果有的话），然后执行 return。

         在 catch 块内的 return：
         在 catch 块内使用 return 可以将异常捕获并返回特定的值。这在需要处理异常并返回默认值的情况下很有用。

         return;用于终止代码块或者终止方法的运行,break用于终止循环,return;后面不能再接其他语句,但是其所在的方法外还是可以有其他语句的.
         */
        try {
            int a = 10 / 0;
            System.out.println("正常时执行该语句!");
        } catch (Exception e) {
            System.out.println("异常时执行该语句!");
            e.printStackTrace();
        } finally {
            System.out.println("正常和异常都会执行!");
        }
        System.out.println(divide(10, 2));
        System.out.println(divide(10, 0));
    }

    public static int divide(int a, int b) {
        try {
            return a / b; // This can throw ArithmeticException if b is 0
        } catch (ArithmeticException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return 0; // Return a default value in case of division by zero
        } finally {
            System.out.println("Finally block executed");
        }
    }

}
