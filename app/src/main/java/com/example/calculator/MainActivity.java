package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    private Button
            mbtnMc,mbtnC,mbtnDrg,mbtnSin,
            mbtnCos,mbtnTan, mbtnFactorial,mbtnBack,
            mbtnAdd,mbtnSub, mbtnMul,mbtnDivide,mbtnLeft,mbtnRight,
            mbtnSqrt,mbtnSquare,mbtnLog,mbtnLn,
            mbtnDot,mbtnEqual,mbtnExit;
    private Button[] btn = new Button[10];
    private EditText input;
    private TextView _drg;
    private TextView mem;
    private TextView tip;
    public String str_old;
    public String str_new;
    public boolean vbegin = true;
    public boolean drg_flag = true;
    public double pi = 4*Math.atan(1);
    public boolean tip_lock = true;
    public boolean equals_flag = true;
    String[] Tipcommand = new String[500];
    int tip_i = 0;
    private View.OnClickListener actionPerformed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String command = ((Button)v).getText().toString();
            String str = input.getText().toString();
            if(equals_flag == false&&"0123456789.sincostanlnlogn!+-×÷√^".indexOf(command) != -1){
                if(right(str)){
                    if("+-×÷√^".indexOf(command) != -1){
                        for(int i = 0;i <str.length();i++){
                            Tipcommand[tip_i] = String.valueOf(str.charAt(i));
                            tip_i++;
                         }
                        vbegin = false;
                    }
                }else{
                    input.setText("0");
                    vbegin = true;
                    tip_i = 0;
                    tip_lock = true;
                    tip.setText("欢迎使用！");
                }
                equals_flag = true;
            }
            if(tip_i > 0){
                TipChecker(Tipcommand[tip_i-1],command);
            }else if(tip_i == 0){
                TipChecker("#",command);
            }
            if("0123456789.sincostanlnlogn!+-×÷√^".indexOf(command) != -1 && tip_lock){
                Tipcommand[tip_i] = command;
                tip_i++;
            }
            if("0123456789.sincostanlnlogn!+-×÷√^".indexOf(command) != -1 && tip_lock){
                print(command);
            }else if(command.compareTo("DRG") == 0 && tip_lock){
                if(drg_flag == true){
                    drg_flag = false;
                    _drg.setText("  RAD");
                }else{
                    drg_flag = true;
                    _drg.setText("  DEG");
                }
            }else if(command.compareTo("Back") == 0 && equals_flag){
                if(TTO(str) == 3){
                    if(str.length() > 3){
                        input.setText(str.substring(0,str.length()-3));
                    }else if(str.length() == 3){
                        input.setText("0");
                        vbegin = true;
                        tip_i = 0;
                        tip.setText("欢迎使用!");
                    }
                }else if(TTO(str) == 2){
                    if(str.length() > 2){
                        input.setText(str.substring(0,str.length()-2));
                    }else if(str.length() == 2){
                        input.setText("0");
                        vbegin = true;
                        tip_i = 0;
                        tip.setText("欢迎使用!");
                    }
                }else if(TTO(str) == 1){
                    if(right(str)){
                        if(str.length() > 1){
                            input.setText(str.substring(0,str.length()-1));
                        }else if(str.length() == 1){
                            input.setText("0");
                            vbegin = true;
                            tip_i = 0;
                            tip.setText("欢迎使用!");
                        }
                    }else{
                        input.setText("0");
                        vbegin = true;
                        tip_i = 0;
                        tip.setText("欢迎使用!");
                    }
                }
                if(input.getText().toString().compareTo("-") == 0 || equals_flag == false){
                    input.setText("0");
                    vbegin = true;
                    tip_i = 0;
                    tip.setText("欢迎使用!");
                }
                tip_lock = true;
                if(tip_i > 0)
                    tip_i--;
            }else if(command.compareTo("Back") == 0 && equals_flag == false){
                input.setText("0");
                vbegin = true;
                tip_i = 0;
                tip_lock = true;
                tip.setText("欢迎使用!");
            }else if(command.compareTo("C") == 0){
                input.setText("0");
                vbegin = true;
                tip_i = 0;
                tip_lock = true;
                equals_flag = true;
                tip.setText("欢迎使用！");
            }else if(command.compareTo("MC") == 0){
                mem.setText("0");
            }else if(command.compareTo("exit") == 0){
                System.exit(0);
            }else if(command.compareTo("=") == 0 && tip_lock && right(str) && equals_flag){
                tip_i = 0;
                tip_lock = false;
                equals_flag = false;
                str_old = str;

                str = str.replaceAll("sin","s");
                str = str.replaceAll("cos","c");
                str = str.replaceAll("tan","t");
                str = str.replaceAll("log","g");
                str = str.replaceAll("ln","l");
                str = str.replaceAll("n!","!");
                vbegin = true;
                str_new = str.replaceAll("-","-1×");
                new calc().process(str_new);
            }
            tip_lock = true;
        }
    };
    private void print(String str){
        if(vbegin){
            input.setText(str);
        }else{
            input.append(str);
        }
        vbegin = false;
    }
    private boolean right(String str){
        int i = 0;
        for(i = 0;i < str.length();i++){
            if(str.charAt(i) != '0' && str.charAt(i) != '1' && str.charAt(i) != '2'
                && str.charAt(i) != '3' && str.charAt(i) != '4' && str.charAt(i) != '5' &&
                   str.charAt(i) != '6' && str.charAt(i) != '7' && str.charAt(i) != '8' &&
                   str.charAt(i) != '9' && str.charAt(i) != '.' && str.charAt(i) != '-' &&
                   str.charAt(i) != '+' && str.charAt(i) != '×' && str.charAt(i) != '÷' &&
                   str.charAt(i) != '√' && str.charAt(i) != '^' && str.charAt(i) != 's' &&
                    str.charAt(i) != 'i' && str.charAt(i) != 'n' && str.charAt(i) != 'c' &&
                    str.charAt(i) != 'o' && str.charAt(i) != 't' && str.charAt(i) != 'a' &&
                    str.charAt(i) != 'l' && str.charAt(i) != 'g' && str.charAt(i) != '(' &&
                    str.charAt(i) != ')' && str.charAt(i) != '!'){
                break;
            }
        }
        if(i == str.length()){
            return true;
        }else{
            return  false;
        }
    }
    private int TTO(String str){
        if((str.charAt(str.length()-1) == 'n' && str.charAt(str.length()-2) == 'i' && str.charAt(str.length()-3) == 's' )||
                (str.charAt(str.length() - 1) == 's' && str.charAt(str.length() - 2) == 'o' && str.charAt(str.length() - 3) == 'c') ||
                (str.charAt(str.length() - 1) == 'n' && str.charAt(str.length() - 2) == 'a' && str.charAt(str.length() - 3) == 't') ||
                (str.charAt(str.length() - 1) == 'g' && str.charAt(str.length() - 2) == 'o' && str.charAt(str.length() - 3) == 'l')){
            return 3;
        }else if((str.charAt(str.length() - 1) == 'n' && str.charAt(str.length() - 2) == 'l') ||
                (str.charAt(str.length() - 1) == '!' && str.charAt(str.length() - 2) == 'n')){
            return 2;
        }else{
            return 1;
        }
    }
    private void TipChecker(String tipcommand1,String tipcommand2){
        int Tipcode1 = 0;
        int Tipcode2 = 0;
        int tiptype1 = 0;
        int tiptype2 = 0;
        int bracket = 0;
        if(tipcommand1.compareTo("#") == 0 && (tipcommand2.compareTo("÷") == 0 || tipcommand2.compareTo("×") == 0 ||
                tipcommand2.compareTo("+") == 0 || tipcommand2.compareTo(")") == 0 || tipcommand2.compareTo("√") == 0 ||
                tipcommand2.compareTo("^") == 0)){
            Tipcode1 = -1;
        }
        else if(tipcommand1.compareTo("#") != 0){
            if(tipcommand1.compareTo("(") == 0){
                tiptype1 = 1;
            }else if(tipcommand1.compareTo(")") == 0){
                tiptype1 = 2;
            }else if(tipcommand1.compareTo(".") == 0){
                tiptype1 = 3;
            }else if("0123456789".indexOf(tipcommand1) != -1){
                tiptype1 = 4;
            }else if("+-×÷".indexOf(tipcommand1) != -1){
                tiptype1 = 5;
            }else if("√^".indexOf(tipcommand1) != -1){
                tiptype1 = 6;
            }else if("sincostanlnlogn!".indexOf(tipcommand1) != -1){
                tiptype1 = 7;
            }
            if(tipcommand2.compareTo("(") == 0){
                tiptype2 = 1;
            }else if(tipcommand2.compareTo(")") == 0){
                tiptype2 = 2;
            }else if(tipcommand2.compareTo(".") == 0){
                tiptype2 = 3;
            }else if("0123456789".indexOf(tipcommand2) != -1){
                tiptype2 = 4;
            }else if("+-×÷".indexOf(tipcommand2) != -1){
                tiptype2 = 5;
            }else if("√^".indexOf(tipcommand2) != -1){
                tiptype2 = 6;
            }else if("sincostanlnlogn!".indexOf(tipcommand2) != -1){
                tiptype2 = 7;
            }
            switch (tiptype1){
                case 1:
                    if(tiptype2 == 2 || (tiptype2 == 5 && tipcommand2.compareTo("-") != 0) || tiptype2 == 6){
                        Tipcode1 = 1;
                    }
                    break;
                case 2:
                    if(tiptype2 == 1 || tiptype2 == 3 || tiptype2 == 4 || tiptype2 == 7){
                        Tipcode1 = 2;
                    }
                    break;
                case 3:
                    if(tiptype2 == 1 || tiptype2 == 7){
                        Tipcode1 = 3;
                    }
                    if(tiptype2 == 3){
                        Tipcode1 = 8;
                    }
                    break;
                case 4:
                    if(tiptype2 == 1 || tiptype2 == 7){
                        Tipcode1 = 4;
                    }
                    break;
                case 5:
                    if(tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6){
                        Tipcode1 = 5;
                    }
                    break;
                case 6:
                    if(tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6 ||tiptype2 == 7){
                        Tipcode1 = 6;
                    }
                    break;
                case 7:
                    if(tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6 || tiptype2 == 7){
                        Tipcode1 = 7;
                    }
                    break;
            }
        }
        if(Tipcode1 == 0 && tipcommand2.compareTo(".") == 0){
            int tip_point = 0;
            for(int i = 0;i < tip_i;i++){
                if(Tipcommand[i].compareTo(".") == 0){
                    tip_point++;
                }
                if(Tipcommand[i].compareTo("sin") == 0 || Tipcommand[i].compareTo("cos") == 0 ||
                        Tipcommand[i].compareTo("tan") == 0 || Tipcommand[i].compareTo("log") == 0 ||
                        Tipcommand[i].compareTo("ln") == 0 || Tipcommand[i].compareTo("n!") == 0 ||
                        Tipcommand[i].compareTo("√") == 0 || Tipcommand[i].compareTo("^") == 0 ||
                        Tipcommand[i].compareTo("×") == 0 || Tipcommand[i].compareTo("÷") == 0 ||
                        Tipcommand[i].compareTo("-") == 0 || Tipcommand[i].compareTo("+") == 0 ||
                        Tipcommand[i].compareTo("(") == 0 || Tipcommand[i].compareTo(")") == 0){
                    tip_point = 0;
                }
            }
            tip_point++;
            if(tip_point > 1){
                Tipcode1 = 8;
            }
        }
        if(Tipcode1 == 0 && tipcommand2.compareTo(")") == 0){
            int tip_right_bracket = 0;
            for(int i = 0;i < tip_i;i++){
                if(Tipcommand[i].compareTo("(") == 0){
                    tip_right_bracket++;
                }
                if(Tipcommand[i].compareTo(")") == 0){
                    tip_right_bracket--;
                }
            }
            if(tip_right_bracket == 0){
                Tipcode1 = 10;
            }
        }
        if(Tipcode1 == 0 && tipcommand2.compareTo("=") == 0){
            int tip_bracket = 0;
            for(int i = 0;i < tip_i;i++){
                if(Tipcommand[i].compareTo("(") == 0){
                    tip_bracket++;
                }
                if(Tipcommand[i].compareTo(")") == 0){
                    tip_bracket--;
                }
            }
            if(tip_bracket > 0){
                Tipcode1 = 9;
                bracket = tip_bracket;
            }else if(tip_bracket == 0){
                if("√^sincostanlnlogn!".indexOf(tipcommand1) != -1){
                    Tipcode1 = 6;
                }
                if("+-×÷".indexOf(tipcommand1) != -1){
                    Tipcode1 = 5;
                }
            }
        }
        if(tipcommand2.compareTo("MC") == 0) Tipcode2 = 1;
        if(tipcommand2.compareTo("C") == 0) Tipcode2 = 2;
        if(tipcommand2.compareTo("DRG") == 0) Tipcode2 = 3;
        if(tipcommand2.compareTo("Back") == 0) Tipcode2 = 4;
        if(tipcommand2.compareTo("sin") == 0) Tipcode2 = 5;
        if(tipcommand2.compareTo("cos") == 0) Tipcode2 = 6;
        if(tipcommand2.compareTo("tan") == 0) Tipcode2 = 7;
        if(tipcommand2.compareTo("log") == 0) Tipcode2 = 8;
        if(tipcommand2.compareTo("ln") == 0) Tipcode2 = 9;
        if(tipcommand2.compareTo("n") == 0) Tipcode2 = 10;
        if(tipcommand2.compareTo("√") == 0) Tipcode2 = 11;
        if(tipcommand2.compareTo("^") == 0) Tipcode2 = 12;

        TipShow(bracket,Tipcode1,Tipcode2,tipcommand1,tipcommand2);
    }
    private void TipShow(int bracket,int tipcode1,int tipcode2,String tipcommand1,String tipcommand2){
        String tipmessage = "";
        if(tipcode1 != 0)
        {
            tip_lock = false;
        }
        switch (tipcode1){
            case -1:
                tipmessage = tipcommand2 + "  不能做为第一个运算符\n";
                break;
            case 1:
                tipmessage = tipcommand1 + "  后应输入：数字/(/./-/函数 \n";
                break;
            case 2:
                tipmessage = tipcommand1 + "  后应输入：)/算符 \n";
                break;
            case 3:
                tipmessage = tipcommand1 + "  后应输入：)/数字/算符 \n";
                break;
            case 4:
                tipmessage = tipcommand1 + "  后应输入：)/./数字/算符 \n";
                break;
            case 5:
                tipmessage = tipcommand1 + "  后应输入：(/./数字/函数 \n";
                break;
            case 6:
                tipmessage = tipcommand1 + "  后应输入：(/./数字 \n";
                break;
            case 7:
                tipmessage = tipcommand1 + "  后应输入：(/./数字 \n";
                break;
            case 8:
                tipmessage = "小数点重复\n";
                break;
            case 9:
                tipmessage = "不能计算，缺少 "+ bracket + " 个 )";
                break;
            case 10:
                tipmessage = "不需要 )";
                break;
        }
        switch (tipcode2){
            case 1:
                tipmessage = tipmessage + "[MC 用法：清除记忆 MEM]";
                break;
            case 2:
                tipmessage = tipmessage + "[C 用法：归零]";
                break;
            case 3:
                tipmessage = tipmessage + "[DRG 用法：选择DEG或RAD]";
                break;
            case 4:
                tipmessage = tipmessage + "[Back 用法：退格]";
                break;
            case 5:
                tipmessage = tipmessage + "sin 函数用法示例：\n" +
                        "DEG:sin30 = 0.5    RAD:sin1 = 0.84\n" +
                        "注：与其他函数一起使用时要加括号，如：\n" +
                        "sin(cos45), 而不是sincos45";
                break;
            case 6:
                tipmessage = tipmessage + "cos 函数用法示例：\n" +
                        "DEG:cos60 = 0.5    RAD:cos1 = 0.54\n" +
                        "注：与其他函数一起使用时要加括号，如：\n" +
                        "cos(sin45), 而不是cossin45";
                break;
            case 7:
                tipmessage = tipmessage + "tan 函数用法示例：\n" +
                        "DEG:cos45 = 1    RAD:cos1 = 1.55\n" +
                        "注：与其他函数一起使用时要加括号，如：\n" +
                        "tan(cos45), 而不是tancos45";
                break;
            case 8:
                tipmessage = tipmessage + "log 函数用法示例：\n" +
                        "log10 = log(5+5) = 1\n" +
                        "注：与其他函数一起使用时要加括号，如：\n" +
                        "log(tan45), 而不是logtan45";
                break;
            case 9:
                tipmessage = tipmessage + "ln 函数用法示例：\n" +
                        "ln10 = le(5+5) = 2.3    lne = 1\n" +
                        "注：与其他函数一起使用时要加括号，如：\n" +
                        "ln(sin45), 而不是lnsin45";
                break;
            case 10:
                tipmessage = tipmessage + "n! 函数用法示例：\n" +
                        "n!3 = n!(1+2) = 3*2*1 = 6\n" +
                        "注：与其他函数一起使用时要加括号，如：\n" +
                        "n!(log1000), 而不是n!log1000";
                break;
            case 11:
                tipmessage = tipmessage + "√ 函数用法示例：开任意次根号\n" +
                        "如：27开三次根为    27√3 = 3\n" +
                        "注：与其他函数一起使用时要加括号，如：\n" +
                        "(函数)√(函数) ,(n!3）√(log100) = 2.45";
                break;
            case 12:
                tipmessage = tipmessage + "^ 函数用法示例：开任意次平方\n" +
                        "如：2的三次方    2^3 = 8\n" +
                        "注：与其他函数一起使用时要加括号，如：\n" +
                        "(函数)^(函数) ,(n!3）^(log100) = 36";
                break;
        }
        tip.setText(tipmessage);
    }

    public class calc{
        public calc(){

        }
        final int MAXLEN = 500;

        public void process(String str){
            int weightPlus = 0,topOp = 0,topNum = 0,flag = 1,weightTemp = 0;
            int weight[];
            double number[];
            char ch,ch_gai,operator[];
            String num;
            weight = new int[MAXLEN];
            number = new double[MAXLEN];
            operator = new char[MAXLEN];
            String expression = str;
            StringTokenizer expToken = new StringTokenizer(expression,"+-×÷()sctgl!√^");
            int i = 0;
            while(i < expression.length()){
                ch = expression.charAt(i);
                if(i == 0){
                    if(ch == '-')
                        flag = -1;
                }else if(expression.charAt(i-1) == '(' && ch == '-')
                    flag = -1;
                if(ch <= '9' && ch >= '0' || ch == '.' || ch == 'E'){
                    num = expToken.nextToken();
                    ch_gai = ch;
                    Log.e("guojs",ch+"--->"+i);
                    while(i < expression.length() && (ch_gai <= '9' && ch_gai >= '0' || ch_gai == '.' || ch_gai == 'E')){
                        ch_gai = expression.charAt(i++);
                        Log.e("guojs","i的值为："+i);
                    }
                    if(i >= expression.length()) i -= 1; else {i-=2;}
                    if(num.compareTo(".") == 0) number[topNum++] = 0;
                    else {
                        number[topNum++] = Double.parseDouble(num)*flag;
                        flag = 1;
                    }
                }
                if(ch == '(') weightPlus += 4;
                if(ch == ')') weightPlus -= 4;
                if(ch == '-' && flag == 1 || ch == '+' || ch == '×' || ch == '÷' ||
                    ch == 's' || ch == 'c' || ch == 't' || ch == 'g' || ch == 'l' ||
                    ch == '!' || ch == '√' || ch == '^'){
                    switch (ch){
                        case '+':
                        case '-':
                            weightTemp = 1 + weightPlus;
                            break;
                        case '×':
                        case '÷':
                            weightTemp = 2 + weightPlus;
                            break;
                        case 's':
                        case 'c':
                        case 't':
                        case 'g':
                        case 'l':
                        case '!':
                            weightTemp = 3+ weightPlus;
                            break;
                        case '^':
                        case '√':
                        default:
                            weightTemp = 4 + weightPlus;
                            break;
                    }
                    if(topOp == 0 || weight[topOp-1] < weightTemp){
                        weight[topOp] = weightTemp;
                        operator[topOp] = ch;
                        topOp++;
                    }else{
                        while(topOp > 0 && weight[topOp-1] >= weightTemp){
                            switch (operator[topOp-1]){
                                case '+':
                                    number[topNum-2] += number[topNum-1];

                                    break;
                                case '-':
                                    number[topNum-2] -= number[topNum-1];
                                    break;
                                case '×':
                                    number[topNum-2] *= number[topNum-1];
                                    break;
                                case '÷':
                                    if(number[topOp-1] == 0){
                                        showError(1,str_old);
                                        return;
                                    }
                                    number[topNum-2] /= number[topNum-1];
                                    break;
                                case '√':
                                    if(number[topNum-1] == 0 || (number[topNum-2] < 0 && number[topNum-1]%2 == 0)){
                                        showError(2,str_old);
                                        return;
                                    }
                                    number[topNum-2] = Math.pow(number[topNum-2],1/number[topNum-1]);
                                    break;
                                case '^':
                                    number[topNum-2] = Math.pow(number[topNum-2],number[topNum-1]);
                                    break;
                                case 's':
                                    if(drg_flag = true){
                                        number[topNum-1] = Math.sin((number[topNum-1]/180)*pi);
                                    }else{
                                        number[topNum-1] = Math.sin(number[topNum-1]);
                                    }
                                    topNum++;
                                    break;
                                case 'c':
                                    if(drg_flag == true){
                                        number[topNum-1] = Math.cos((number[topNum-1]/180)*pi);
                                    }else{
                                        number[topNum-1] = Math.cos(number[topNum-1]);
                                    }
                                    topNum++;
                                    break;
                                case 't':
                                    if(drg_flag == true){
                                        if((Math.abs(number[topNum-1])/90)%2 == 1){
                                            showError(2,str_old);
                                            return;
                                        }
                                        number[topNum-1] = Math.abs((number[topNum-1]/180)*pi);
                                    }else{
                                        if((Math.abs(number[topNum-1])/(pi/2))%2 == 1){
                                            showError(2,str_old);
                                            return;
                                        }
                                        number[topNum-1] = Math.abs(number[topNum-1]);
                                    }
                                    topNum++;
                                    break;
                                case 'g':
                                    if(number[topNum-1] <= 0){
                                        showError(2,str_old);
                                        return;
                                    }
                                    number[topNum-1] = Math.log10(number[topNum-1]);
                                    topNum++;
                                    break;
                                case 'l':
                                    if(number[topNum-1] <= 0){
                                        showError(2,str_old);
                                        return;
                                    }
                                    number[topNum-1] = Math.log(number[topNum-1]);
                                    topNum++;
                                    break;
                                case '!':
                                    if(number[topNum-1] > 170){
                                        showError(3,str_old);
                                        return;
                                    }else if(number[topNum-1] < 0){
                                        showError(2,str_old);
                                        return;
                                    }
                                    number[topNum-1] = N(number[topNum-1]);
                                    topNum++;
                                    break;
                            }
                            topNum--;
                            topOp--;
                        }
                        weight[topOp] = weightTemp;
                        operator[topOp] = ch;
                        topOp++;
                    }
                }
                i++;
            }
            while(topOp > 0){
                switch (operator[topOp-1]){
                    case '+':
                        number[topNum-2] += number[topNum-1];
                        break;
                    case '-':
                        number[topNum-2] -= number[topNum-1];
                        break;
                    case '×':
                        number[topNum-2] *= number[topNum-1];
                        break;
                    case '÷':
                        if(number[topNum-1] == 0){
                            showError(1,str_old);
                            return;
                        }
                        number[topNum-2] /=number[topNum-1];
                        break;
                    case '√':
                        if(number[topNum-1] == 0 || (number[topNum-2] < 0 && number[topNum-1]%2 == 0)){
                            showError(2,str_old);
                        }
                        number[topNum-2] = Math.pow(number[topNum-2],1/number[topNum-1]);
                        break;
                    case '^':
                        number[topNum-2] = Math.pow(number[topNum-2],number[topNum-1]);
                        break;
                    case 's':
                        if(drg_flag = true){
                            number[topNum-1] = Math.sin((number[topNum-1]/180)*pi);
                        }else{
                            number[topNum-1] = Math.sin(number[topNum-1]);
                        }
                        topNum++;
                        break;
                    case 'c':
                        if(drg_flag == true){
                            number[topNum-1] = Math.cos((number[topNum-1]/180)*pi);
                        }else{
                            number[topNum-1] = Math.cos(number[topNum-1]);
                        }
                        topNum++;
                        break;
                    case 't':
                        if(drg_flag == true){
                            if((Math.abs(number[topNum-1])/90)%2 == 1){
                                showError(2,str_old);
                                return;
                            }
                            number[topNum-1] = Math.abs((number[topNum-1]/180)*pi);
                        }else{
                            if((Math.abs(number[topNum-1])/(pi/2))%2 == 1){
                                showError(2,str_old);
                                return;
                            }
                            number[topNum-1] = Math.abs(number[topNum-1]);
                        }
                        topNum++;
                        break;
                    case 'g':
                        if(number[topNum-1] <= 0){
                            showError(2,str_old);
                            return;
                        }
                        number[topNum-1] = Math.log10(number[topNum-1]);
                        topNum++;
                        break;
                    case 'l':
                        if(number[topNum-1] <= 0){
                            showError(2,str_old);
                            return;
                        }
                        number[topNum-1] = Math.log(number[topNum-1]);
                        topNum++;
                        break;
                    case '!':
                        if(number[topNum-1] > 170){
                            showError(3,str_old);
                            return;
                        }else if(number[topNum-1] < 0){
                            showError(2,str_old);
                            return;
                        }
                        number[topNum-1] = N(number[topNum-1]);
                        topNum++;
                        break;
                }
                topNum--;
                topOp--;
            }
            if(number[0] > 7.3E306){
                showError(3,str_old);
                return;
            }
            input.setText(String.valueOf(EP(number[0])));
            tip.setText("计算完毕，要继续请按归零键 C");
            mem.setText(str_old+"="+String.valueOf(EP(number[0])));
        }
        public double EP(double n){
            DecimalFormat format = new DecimalFormat("0.#############");
            return Double.parseDouble(format.format(n));
        }
        public double N(double n){
            int i = 0;
            double sum = 1;
            for(i = 1;i <= n;i++){
                sum = sum*i;
            }
            return sum;
        }
        public void showError(int code,String str){
            String message = "";
            switch (code){
                case 1:
                    message = "零不能作除数";
                    break;
                case 2:
                    message = "函数格式错误";
                    break;
                case 3:
                    message = "值太大了，超出范围";
                    break;
            }
            input.setText("\""+str+"\""+": "+message);
            tip.setText(message+"\n"+"计算完毕，要继续请按归零键 C");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.et_input);
        mem = findViewById(R.id.tv_mem);
        tip = findViewById(R.id.tv_tip);
        _drg = findViewById(R.id.tv_drg);
        btn[0] = findViewById(R.id.btn_zero);
        btn[1] = findViewById(R.id.btn_one);
        btn[2] = findViewById(R.id.btn_two);
        btn[3] = findViewById(R.id.btn_three);
        btn[4] = findViewById(R.id.btn_four);
        btn[5] = findViewById(R.id.btn_five);
        btn[6] = findViewById(R.id.btn_six);
        btn[7] = findViewById(R.id.btn_seven);
        btn[8] = findViewById(R.id.btn_eight);
        btn[9] = findViewById(R.id.btn_nine);
        mbtnDivide = findViewById(R.id.btn_divide);
        mbtnMul = findViewById(R.id.btn_mul);
        mbtnSub = findViewById(R.id.btn_sub);
        mbtnAdd = findViewById(R.id.btn_add);
        mbtnEqual = findViewById(R.id.btn_equal);
        mbtnSin = findViewById(R.id.btn_sin);
        mbtnCos = findViewById(R.id.btn_cos);
        mbtnTan = findViewById(R.id.btn_tan);
        mbtnLog = findViewById(R.id.btn_log);
        mbtnLn = findViewById(R.id.btn_ln);
        mbtnSqrt = findViewById(R.id.btn_sqrt);
        mbtnSquare = findViewById(R.id.btn_square);
        mbtnFactorial = findViewById(R.id.btn_factorial);
        mbtnBack = findViewById(R.id.btn_back);
        mbtnLeft = findViewById(R.id.btn_left);
        mbtnRight = findViewById(R.id.btn_right);
        mbtnDot = findViewById(R.id.btn_dot);
        mbtnExit = findViewById(R.id.btn_exit);
        mbtnDrg = findViewById(R.id.btn_drg);
        mbtnMc = findViewById(R.id.btn_mc);
        mbtnC = findViewById(R.id.btn_c);

        for(int i = 0;i < 10;i++)
        {
            btn[i].setOnClickListener(actionPerformed);
        }
        mbtnDivide.setOnClickListener(actionPerformed);
        mbtnMul.setOnClickListener(actionPerformed);
        mbtnSub.setOnClickListener(actionPerformed);
        mbtnAdd.setOnClickListener(actionPerformed);
        mbtnEqual.setOnClickListener(actionPerformed);
        mbtnSin.setOnClickListener(actionPerformed);
        mbtnCos.setOnClickListener(actionPerformed);
        mbtnTan.setOnClickListener(actionPerformed);
        mbtnLog.setOnClickListener(actionPerformed);
        mbtnLn.setOnClickListener(actionPerformed);
        mbtnSqrt.setOnClickListener(actionPerformed);
        mbtnSquare.setOnClickListener(actionPerformed);
        mbtnFactorial.setOnClickListener(actionPerformed);
        mbtnBack.setOnClickListener(actionPerformed);
        mbtnLeft.setOnClickListener(actionPerformed);
        mbtnRight.setOnClickListener(actionPerformed);
        mbtnDot.setOnClickListener(actionPerformed);
        mbtnExit.setOnClickListener(actionPerformed);
        mbtnDrg.setOnClickListener(actionPerformed);
        mbtnMc.setOnClickListener(actionPerformed);
        mbtnC.setOnClickListener(actionPerformed);
    }
}