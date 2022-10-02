import com.sun.source.doctree.ThrowsTree;

import javax.management.StringValueExp;
import javax.swing.*;
import javax.swing.plaf.synth.SynthToggleButtonUI;
import java.awt.*;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

public class MainClass {

    JFrame frame;
    JLabel label;
    JPanel panel;
    public MainClass() {

        frame = new JFrame();
        frame.setTitle("Calculator");
//        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.lightGray);
        frame.setResizable(true);

        ImageIcon imageIcon = new ImageIcon("calculator.png");
        frame.setIconImage(imageIcon.getImage());


        label = new JLabel("");
        label.setPreferredSize(new Dimension(600, 200));
        label.setForeground(Color.darkGray);
        label.setFont(new Font("Arial", Font.PLAIN, 60));


        panel = new JPanel();
        panel.setPreferredSize(new Dimension(600, 400));
        panel.setLayout(new GridLayout(5, 4, 1, 1));


        JButton button1 = new JButton("1");
        setNumbers(button1);

        JButton button2 = new JButton("2");
        setNumbers(button2);

        JButton button3 = new JButton("3");
        setNumbers(button3);

        JButton button4 = new JButton("4");
        setNumbers(button4);

        JButton button5 = new JButton("5");
        setNumbers(button5);

        JButton button6 = new JButton("6");
        setNumbers(button6);

        JButton button7 = new JButton("7");
        setNumbers(button7);

        JButton button8 = new JButton("8");
        setNumbers(button8);

        JButton button9 = new JButton("9");
        setNumbers(button9);

        JButton button0 = new JButton("0");
        setNumbers(button0);

        JButton buttonDivide = new JButton("/");
        setOperators(buttonDivide);

        JButton buttonDouble = new JButton("*");
        setOperators(buttonDouble);

        JButton buttonPlus = new JButton("+");
        setOperators(buttonPlus);

        JButton buttonMinus = new JButton("-");
        setOperators(buttonMinus);

        JButton buttonPow = new JButton("^");
        setOperators(buttonPow);

        JButton buttonSqrt = new JButton("S");
        buttonSqrt.addActionListener(e -> {
            if (label.getText().equals("")) return;
            double number = Double.parseDouble(label.getText());
            double result1 = Math.sqrt(number);
            int result2 = (int) result1;
            label.setText(String.valueOf(result2));
            if (result1 != result2)
                JOptionPane.showMessageDialog(null, "It's not exact the answer, because I cast it to Integer.", "Error massage!", JOptionPane.PLAIN_MESSAGE);
        });

        JButton buttonReset = new JButton("C");
        buttonReset.addActionListener(e -> {
            label.setText("");
        });

        JButton buttonDelete = new JButton("D");
        buttonDelete.addActionListener(e -> {
            String text = label.getText();
            String subText = text.substring(0, text.length()-1);
            label.setText(subText);
        });

        JButton buttonPoint = new JButton(".");
        buttonPoint.addActionListener(e -> {
//            if (label.getText().equals("")) return;
//            label.setText(label.getText() + ".");
        JOptionPane.showMessageDialog(null, "This button doesn't work right now.", "Error massage!", JOptionPane.PLAIN_MESSAGE);

        });

        JButton buttonEqual = new JButton("=");
        calculate(buttonEqual);

        JButton[] buttons = {buttonSqrt, buttonPow, buttonReset, buttonDelete,
                             button7, button8, button9, buttonPlus,
                             button4, button5, button6, buttonMinus,
                             button1, button2, button3, buttonDouble,
                             buttonPoint, button0, buttonEqual, buttonDivide
        };
        for (JButton b: buttons) {
            b.setFocusable(false);
            panel.add(b);
        }


        frame.add(panel, BorderLayout.SOUTH);
        frame.add(label, BorderLayout.NORTH);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }


    public void setNumbers (JButton button) {
        button.setFont(new Font("MV Boli", Font.BOLD, 20));
        button.addActionListener(e -> {
            if (label.getText().equals("No equation entered!") || label.getText().equals("0")) {
                label.setText("");
            }
            if (label.getText().equals("")) {
                label.setText(button.getText());
            } else {
                label.setText(label.getText()+button.getText());
            }
        });
    }

    public void setOperators (JButton button) {
        button.addActionListener(e -> {
            if (label.getText().equals("") || checkOperators()) return;
            label.setText(label.getText() + button.getText());
        });
    }

    public boolean checkOperators () {
        for (int i = 0; i < label.getText().length(); i++) {
            if (label.getText().charAt(i) == '+' || label.getText().charAt(i) == '-' ||
                label.getText().charAt(i) == '*' || label.getText().charAt(i) == '/' ||
                label.getText().charAt(i) == 'S' || label.getText().charAt(i) == '^') return true;
        }
        return false;
    }

    public void calculate (JButton button) {

         button.addActionListener(e -> {
             if (label.getText().isEmpty()) {
                 label.setText("No equation entered!");
                 return;
             }
             if (label.getText().equals("No equation entered!")) {
                 return;
             }
             Stack<Character> stack = new Stack<>();
             String str = label.getText();
             for (int i = 0; i < str.length(); i++) {
                 stack.push(str.charAt(i));
             }

             int counter = 10;
             int result = 0, number1 = 0, number2 = 0;
             char symbol = '+';
             String toReturn = "";

             if (!stack.isEmpty())
                 number1 = Integer.parseInt(String.valueOf(stack.pop()));
             while (!stack.isEmpty() && stack.peek() <= 57 && stack.peek() >= 48) {
                 number1 += (Integer.parseInt(String.valueOf(stack.pop())) * counter);
                 counter *= 10;
             }
             counter = 10;
             if (!stack.isEmpty()) {
                 symbol = stack.pop();
             }
             if (!stack.isEmpty())
                 number2 = Integer.parseInt(String.valueOf(stack.pop()));
             while (!stack.isEmpty() && stack.peek() <= 57 && stack.peek() >= 48) {
                 number2 += (Integer.parseInt(String.valueOf(stack.pop())) * counter);
                 counter *= 10;
             }

             int helper = number1;
             number1 = number2;
             number2 = helper;

             switch (symbol) {
                 case '+':
                     result = number1 + number2;
                     toReturn = String.valueOf(result);
                     label.setText(toReturn);
                     break;
                 case '-':
                     result = number1 - number2;
                     toReturn = String.valueOf(result);
                     label.setText(toReturn);
                     break;
                 case '*':
                     result = number1 * number2;
                     toReturn = String.valueOf(result);
                     label.setText(toReturn);
                     break;
                 case '/':
                     result = number1 / number2;
                     toReturn = String.valueOf(result);
                     label.setText(toReturn);
                     break;
                 case '^':
                     result = (int)Math.pow(number1, number2);
                     toReturn = String.valueOf(result);
                     label.setText(toReturn);
                     break;
                 default:
                     label.setText("No equation entered!");
             }
         });
     }

    public static void main(String[] args) {
        new MainClass();
    }


}
