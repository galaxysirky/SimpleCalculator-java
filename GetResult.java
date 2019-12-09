import java.util.ArrayDeque;
import java.util.Deque;

import javax.swing.JOptionPane;

public class GetResult
{
    private static String str;
    private static Deque<Character> operators = new ArrayDeque<>();
    private static Deque<Double> operands = new ArrayDeque<>();

    public static String result() throws Exception
    {
        int len = str.length();
        for(int i=0;i<len;++i)
        {
            if(isDigit(i))
            {
                int i1 = i + 1;
                while (i1 < len && (isDigit(i1) || isDot(i1)))
                    ++i1;
                push(Double.parseDouble(str.substring(i,i1)));
                i = i1-1;
            }
            else
            {
                while(canCalculate(str.charAt(i)))
                    calculate();
                push(str.charAt(i));
            }
        }
        while(!empty())
            calculate();
        return String.valueOf(getResultNum());
    }

    private static char getSign()
    {
        return operators.peekLast();
    }

    private static double getNum()
    {
        return operands.peekLast();
    }

    private static void popSign()
    {
        operators.removeLast();
    }

    private static void popNum()
    {
        operands.removeLast();
    }

    private static double popAndGetNum()
    {
        double num = getNum();
        popNum();
        return num;
    }

    private static char popAndGetSign()
    {
        char sign = getSign();
        popSign();
        return sign;
    }

    private static void push(double num)
    {
        operands.addLast(num);
    }

    private static void push(char sign)
    {
        operators.addLast(sign);
    }

    private static double getResultNum()
    {
        if(!operands.isEmpty())
            return getNum();
        return 0.0;
    }

    private static void calculate() throws Exception
    {
        double post = popAndGetNum();
        char sign = popAndGetSign();
        double pre = popAndGetNum();
        double result = 0.0;

        switch(sign)
        {
            case '+':
                result = pre + post;
                break;
            case '-':
                result = pre - post;
                break;
            case '*':
                result = pre * post;
                break;
            case '/':
                if(Math.abs(post) < 1e-6)
                {
                    throw new Exception("除数为0,请重新输入");
                }
                else
                    result = pre / post;
                break;
        }
        push(result);
    }

    private static boolean canCalculate(char sign)
    {
        if(operators.isEmpty())
            return false;
        char t = getSign();
        switch(t)
        {
            case '+':
            case '-':
                return sign == '+' || sign == '-';
            case '*':
            case '/':
                return sign == '+' || sign == '-' || sign == '*' || sign == '/';
        }
        return false;
    }

    private static boolean empty()
    {
        return operators.isEmpty();
    }


    public static boolean valid()
    {
        if(isSign(0) || isDot(0))
            return false;
        for(int i=0;i<str.length();++i)
        {
            if(isSign(i))
            {
                if(isSign(i+1) || isDot(i+1))
                    return false;
            }
            else if(isDot(i))
            {
                if(isSign(i+1) || isDot(i+1))
                    return false;
            }
        }
        return true;
    }

    public static void setExpression(String s)
    {
        str = s;
        operands.clear();
        operators.clear();
    }

    private static boolean isSign(int index)
    {
        if(index < str.length())
        {
            char c = str.charAt(index);
            return c == '+' || c == '-' || c == '*' || c == '/';
        }
        return false;
    }

    private static boolean isDot(int index)
    {
        if(index < str.length())
            return str.charAt(index) == '.';
        return false;
    }

    private static boolean isDigit(int index)
    {
        if(index < str.length())
        {
            char c = str.charAt(index);
            return c >= '0' && c <= '9';
        }
        return false;
    }
}