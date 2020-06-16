import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class Calculator
{
    private JFrame mainFrame = new JFrame("Calculator");
    private JPanel boxField = new JPanel();
    private JTextField box = new JTextField();

    private JPanel buttonsField = new JPanel();
    private JButton num0 = new JButton("0");
    private JButton num1 = new JButton("1");
    private JButton num2 = new JButton("2");
    private JButton num3 = new JButton("3");
    private JButton num4 = new JButton("4");
    private JButton num5 = new JButton("5");
    private JButton num6 = new JButton("6");
    private JButton num7 = new JButton("7");
    private JButton num8 = new JButton("8");
    private JButton num9 = new JButton("9");
    private JButton plus = new JButton("+");
    private JButton minus = new JButton("-");
    private JButton mutiply = new JButton("x");
    private JButton divide = new JButton("/");
    private JButton equal = new JButton("=");
    private JButton dot = new JButton(".");

    public Calculator()
    {
        init();
        setButtonsFont(new Font("alias",Font.PLAIN,18));
        addKeyboardEvent();
        addMouseEvent();
    }

    private void init()
    {
        boxField.setLayout(new FlowLayout());
        box.setPreferredSize(new Dimension(300, 25));
        boxField.add(box);

        buttonsField.setLayout(new GridLayout(4, 4, 20, 20));
        buttonsField.setPreferredSize(new Dimension(300, 300));

        buttonsField.add(num1);
        buttonsField.add(num2);
        buttonsField.add(num3);
        buttonsField.add(plus);

        buttonsField.add(num4);
        buttonsField.add(num5);
        buttonsField.add(num6);
        buttonsField.add(minus);

        buttonsField.add(num7);
        buttonsField.add(num8);
        buttonsField.add(num9);
        buttonsField.add(mutiply);

        buttonsField.add(num0);
        buttonsField.add(dot);
        buttonsField.add(equal);
        buttonsField.add(divide);

        mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        mainFrame.setVisible(true);
        mainFrame.setSize(400, 440);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().add(boxField);
        mainFrame.getContentPane().add(buttonsField);
    }

    private void setButtonsFont(Font font)
    {
        num0.setFont(font);
        num1.setFont(font);
        num2.setFont(font);
        num3.setFont(font);
        num4.setFont(font);
        num5.setFont(font);
        num6.setFont(font);
        num7.setFont(font);
        num8.setFont(font);
        num9.setFont(font);
        plus.setFont(font);
        minus.setFont(font);
        mutiply.setFont(font);
        divide.setFont(font);
        equal.setFont(font);
        dot.setFont(font);
    }

    private void addMouseEvent()
    {
        num0.addActionListener(v -> {
            box.setText(box.getText() + "0");
            mainFrame.requestFocus();
        });

        num1.addActionListener(v -> {
            box.setText(box.getText() + "1");
            mainFrame.requestFocus();
        });

        num2.addActionListener(v -> {
            box.setText(box.getText() + "2");
            mainFrame.requestFocus();
        });

        num3.addActionListener(v -> {
            box.setText(box.getText() + "3");
            mainFrame.requestFocus();
        });

        num4.addActionListener(v -> {
            box.setText(box.getText() + "4");
            mainFrame.requestFocus();
        });

        num5.addActionListener(v -> {
            box.setText(box.getText() + "5");
            mainFrame.requestFocus();
        });

        num6.addActionListener(v -> {
            box.setText(box.getText() + "6");
            mainFrame.requestFocus();
        });

        num7.addActionListener(v -> {
            box.setText(box.getText() + "7");
            mainFrame.requestFocus();
        });

        num8.addActionListener(v -> {
            box.setText(box.getText() + "8");
            mainFrame.requestFocus();
        });

        num9.addActionListener(v -> {
            box.setText(box.getText() + "9");
            mainFrame.requestFocus();
        });

        dot.addActionListener(v -> {
            box.setText(box.getText() + ".");
            mainFrame.requestFocus();
        });

        plus.addActionListener(v -> {
            box.setText(box.getText() + "+");
            mainFrame.requestFocus();
        });

        minus.addActionListener(v -> {
            box.setText(box.getText() + "-");
            mainFrame.requestFocus();
        });

        mutiply.addActionListener(v -> {
            box.setText(box.getText() + "*");
            mainFrame.requestFocus();
        });

        divide.addActionListener(v -> {
            box.setText(box.getText() + "/");
            mainFrame.requestFocus();
        });

        equal.addActionListener(v -> {
            setResult();
            mainFrame.requestFocus();
        });
    }

    private void setResult()
    {
        if (!isEmpty())
        {
            GetResult.setExpression(box.getText());
            try
            {
                JOptionPane.showMessageDialog(null, GetResult.valid() ? GetResult.result() : "表达式错误,请重新输入");
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "除数为0,请重新输入");
            }
            box.setText("");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "表达式为空,请重新输入");
        }
    }

    private boolean isEmpty()
    {
        String str = box.getText();
        return str.isEmpty() || str.isBlank();
    }

    private void addKeyboardEvent()
    {
        mainFrame.addKeyListener(new KeyListener()
        {
            private boolean shift = false;
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_SHIFT)
                    shift = true;
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_0)
                    box.setText(box.getText() + "0");
                else if (e.getKeyCode() == KeyEvent.VK_1)
                    box.setText(box.getText() + "1");
                else if (e.getKeyCode() == KeyEvent.VK_1)
                    box.setText(box.getText() + "1");
                else if (e.getKeyCode() == KeyEvent.VK_2)
                    box.setText(box.getText() + "2");
                else if (e.getKeyCode() == KeyEvent.VK_3)
                    box.setText(box.getText() + "3");
                else if (e.getKeyCode() == KeyEvent.VK_4)
                    box.setText(box.getText() + "4");
                else if (e.getKeyCode() == KeyEvent.VK_5)
                    box.setText(box.getText() + "5");
                else if (e.getKeyCode() == KeyEvent.VK_6)
                    box.setText(box.getText() + "6");
                else if (e.getKeyCode() == KeyEvent.VK_7)
                    box.setText(box.getText() + "7");
                else if (e.getKeyCode() == KeyEvent.VK_8)
                    box.setText(box.getText() + (shift ? "*" : "8"));
                else if (e.getKeyCode() == KeyEvent.VK_9)
                    box.setText(box.getText() + "9");
                else if (e.getKeyCode() == KeyEvent.VK_MINUS)
                    box.setText(box.getText() + "-");
                else if (e.getKeyCode() == KeyEvent.VK_SLASH)
                    box.setText(box.getText() + "/");
                else if (e.getKeyCode() == KeyEvent.VK_PERIOD)
                    box.setText(box.getText() + ".");
                else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    box.setText("");
                else if(e.getKeyCode() == KeyEvent.VK_EQUALS)
                {
                    if(shift)
                        box.setText(box.getText() + "+");
                    else
                        setResult();
                }
                else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                {
                    if(!box.getText().equals(""))
                        box.setText(box.getText().substring(0,box.getText().length()-1));
                }
                else if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    setResult();
                }
                shift = false;
            }

            @Override
            public void keyTyped(KeyEvent e) {}
        });
    }

    public static void main(String[] args) {
        new Calculator();
    }
}

class GetResult
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