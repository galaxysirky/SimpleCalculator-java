import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.DecimalFormat;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Enumeration;
import java.io.File;

import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator
{
    private JFrame mainFrame = new JFrame("计算器");
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
    private JButton dot = new JButton(".");
    private JButton equal = new JButton("=");

    private static final String[] buttonNames = {"num0","num1","num2","num3","num4","num5","num6","num7","num8","num9","plus","minus","divide","dot","equal"};

    public Calculator()
    {
        init();
        setButtonsFont(new Font("微软雅黑",Font.BOLD,17));
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

        mainFrame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("image/icon.png")).getImage());
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
        try
        {
            Method setFontMethod = Class.forName("javax.swing.JButton").getMethod("setFont", Font.class);
            for(int i=0;i<buttonNames.length;++i)
            {
                Field buttonField = getClass().getDeclaredField(buttonNames[i]);
                buttonField.setAccessible(true);
                setFontMethod.invoke(buttonField.get(this), font);
            }
        }
        catch(Exception e)
        {
            showMessage("反射失败！程序退出！");
            mainFrame.dispose();
        }
    }

    private void addMouseEvent()
    {
        final Color defaultBackground = num0.getBackground();
        try
        {
            final String [] text = {"0","1","2","3","4","5","6","7","8","9","+","-","/","."};
            final Method addEventListenerMethod = Class.forName("javax.swing.JButton").getMethod("addMouseListener", MouseListener.class);
            for(int i=0;i<text.length;++i)
            {
                final Field buttonField = getClass().getDeclaredField(buttonNames[i]);
                buttonField.setAccessible(true);
                
                addEventListenerMethod.invoke(buttonField.get(this), new MouseListener()
                {
                    @Override
                    public void mouseClicked​(MouseEvent e)
                    {
                        String s = ((JButton)e.getSource()).getText();
                        if(ExpressionHandle.canInput(box.getText(),s.charAt(0)))
                        {
                            box.setText(box.getText() + s);
                        }
                        mainFrame.requestFocus();
                    }
                    @Override
                    public void mouseEntered​(MouseEvent e)
                    {
                        JButton button = (JButton)e.getSource();
                        button.setBackground(new Color(180,180,180));
                    }
                    @Override
                    public void mouseExited​(MouseEvent e)
                    {
                        ((JButton)e.getSource()).setBackground(defaultBackground);
                    }
                    @Override
                    public void mousePressed​(MouseEvent e){}
                    @Override
                    public void mouseReleased​(MouseEvent e){}
                });
            }
        }
        catch(Exception e)
        {
            showMessage("反射失败！程序退出！");
            mainFrame.dispose();
        }

        mutiply.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked​(MouseEvent e)
            {
                if(ExpressionHandle.canInput(box.getText(),'*'))
                    box.setText(box.getText() + "*");
                mainFrame.requestFocus();
            }
            @Override
            public void mouseEntered​(MouseEvent e)
            {
                JButton button = (JButton)e.getSource();
                button.setBackground(new Color(180,180,180));
            }
            @Override
            public void mouseExited​(MouseEvent e)
            {
                ((JButton)e.getSource()).setBackground(defaultBackground);
            }
            @Override
            public void mousePressed​(MouseEvent e){}
            @Override
            public void mouseReleased​(MouseEvent e){}   
        });

        equal.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked​(MouseEvent e)
            {
                setResult();
                mainFrame.requestFocus();
            }
            @Override
            public void mouseEntered​(MouseEvent e)
            {
                JButton button = (JButton)e.getSource();
                button.setBackground(new Color(180,180,180));
            }
            @Override
            public void mouseExited​(MouseEvent e)
            {
                ((JButton)e.getSource()).setBackground(defaultBackground);
            }
            @Override
            public void mousePressed​(MouseEvent e){}
            @Override
            public void mouseReleased​(MouseEvent e){}   
        });
    }

    private void setResult()
    {
        if (!isEmpty())
        {
            ExpressionHandle.setExpression(box.getText());
            try
            {
                showMessage(ExpressionHandle.valid() ? ExpressionHandle.result() : "表达式错误,请重新输入");
            }
            catch (Exception e)
            {
                showMessage("除数为0,请重新输入");
            }
            box.setText("");
        }
        else
        {
            showMessage("表达式为空,请重新输入");
        }
    }

    private boolean isEmpty()
    {
        String str = box.getText();
        return str == null || str.isEmpty();
    }

    private void addKeyboardEvent()
    {
        mainFrame.addKeyListener(new KeyListener()
        {
            private boolean shift = false;
            private boolean enterNotPressedFirst = true;
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_SHIFT)
                    shift = true;
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if(ExpressionHandle.canInput(box.getText(),e.getKeyChar()))
                {
                    int code = e.getKeyCode();
                    if(code >= KeyEvent.VK_0 && code <= KeyEvent.VK_7)
                        box.setText(box.getText() + (code-KeyEvent.VK_0));
                    else if (code == KeyEvent.VK_8)
                        box.setText(box.getText() + (shift ? "*" : "8"));
                    else if (code == KeyEvent.VK_9)
                        box.setText(box.getText() + "9");
                    else if (code == KeyEvent.VK_MINUS)
                        box.setText(box.getText() + "-");
                    else if (code == KeyEvent.VK_SLASH)
                        box.setText(box.getText() + "/");
                    else if (code == KeyEvent.VK_PERIOD)
                        box.setText(box.getText() + ".");
                    else if (code == KeyEvent.VK_ESCAPE)
                        box.setText("");
                    else if(code == KeyEvent.VK_EQUALS)
                    {
                        if(shift)
                            box.setText(box.getText() + "+");
                        else
                        {
                            if(enterNotPressedFirst)
                            {
                                setResult();
                                enterNotPressedFirst = false;
                            }
                            else
                                enterNotPressedFirst = true;
                        }
                    }
                    else if (code == KeyEvent.VK_BACK_SPACE)
                    {
                        if(!box.getText().equals(""))
                            box.setText(box.getText().substring(0,box.getText().length()-1));
                    }
                    else if (code == KeyEvent.VK_ENTER)
                    {            
                        if(enterNotPressedFirst)
                        {
                            setResult();
                            enterNotPressedFirst = false;
                        }
                        else
                            enterNotPressedFirst = true;
                    }
                    shift = false;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}
        });
    }

    private void showMessage(String message)
    {
        JOptionPane.showMessageDialog(null, message,"提示信息",JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new Calculator();
    }
}

class ExpressionHandle
{
    private static String str;
    private static Deque<Character> operators = new ArrayDeque<>();
    private static Deque<Double> operands = new ArrayDeque<>();

    public static boolean canInput(String s,char c)
    {
        str = s;
        if(s == null || s.isEmpty())
            return isDigit(c);
        char lastChar = s.charAt(s.length()-1);
        if(isDigit(lastChar))
            return true;
        if((isSign(c) || isDot(c)) && (isSign(lastChar) || isDot(lastChar)))
            return false;
        return true;
    }

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
        
        String res = String.format("%.5f", getResultNum());
        if(res.indexOf(".") != -1)
        {
            char [] c = res.toCharArray();
            for(len = c.length - 1 ; len>=0 ; --len)
            {
                if(c[len] != '0')
                    break;
            }
            if(c[len] == '.')
                --len;
            return res.substring(0, len+1);
        }
        return res;
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
        int len = str.length();
        for(int i=0;i<len;++i)
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
        if(isSign(len-1) || isDot(len-1))
            return false;
        return true;
    }

    public static void setExpression(String s)
    {
        str = s;
        operands.clear();
        operators.clear();
    }

    private static boolean isSign(char c)
    {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static boolean isSign(int index)
    {
        return index < str.length() ? isSign(str.charAt(index)) : false;
    }

    private static boolean isDot(int index)
    {
        return index < str.length() ? isDot(str.charAt(index)) : false;
    }

    private static boolean isDot(char c)
    {
        return c == '.';
    }

    private static boolean isDigit(char c)
    {
        return c >= '0' && c <= '9';
    }

    private static boolean isDigit(int index)
    {
        return index < str.length() ? isDigit(str.charAt(index)) : false;
    }
}