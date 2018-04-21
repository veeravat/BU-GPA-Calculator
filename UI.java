import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.border.Border;
import javax.swing.*;
import java.io.*;
public class UI extends JFrame {
    private int maxCourse = 7;
    private JPanel Panel;
    private JPanel Panel2;
    private JPanel contentPane;
    private int couse_cnt = -1;
    private JTextField[] CourseName = new JTextField[maxCourse];
    private JComboBox[] gradeBox = new JComboBox[maxCourse];
    private JLabel[] Course = new JLabel[maxCourse];
    private String[] courseCode = new String[maxCourse];
    private String[] courseName = new String[maxCourse];
    private int[] courseCredit = new int[maxCourse];
    private double[] courseGrade = new double[maxCourse];
    private double totalCredit=0,totalGrade=0;
    private boolean validate = true;
    //Constructor 
    public UI(File myDB){

        this.setTitle("Grade Calculator");
        this.setSize(800,600);

        contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(800,600));
        contentPane.setBackground(new Color(255,255,255));

        JLabel logo = new JLabel(new ImageIcon("logo.png"));
        logo.setBounds(0,0,800,200);

        JLabel xIcon = new JLabel(new ImageIcon("xicon.png"));
        xIcon.setBounds(758,2,40,43);
        xIcon.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent evt) {
                    myDB.delete();
                    dispose();
                }
            });

        JLabel mIcon = new JLabel(new ImageIcon("micon.png"));
        mIcon.setBounds(713,2,40,43);
        mIcon.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    setState(JFrame.ICONIFIED);
                }
            });

        JLabel nextBtn = new JLabel(new ImageIcon("nextBtn.png"));
        nextBtn.setBounds(600,0,200,400);
        nextBtn.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    if(checkField()){
                        calculateGrade(myDB);
                        if(validate){
                            contentPane.remove(Panel);
                            reportPanel();
                            revalidate();
                            repaint();
                        }else{
                            String msg = "ชื่อวิชาที่กรอกไม่ถูกต้อง \n-ตรวจสอบชื่อวิชา\n-ใช้พิมพ์เล็กหรือพิมพ์ใหญ่ก็ได้";
                            JOptionPane.showMessageDialog(null,msg, "Error",
                                JOptionPane.ERROR_MESSAGE);
                            validate = true;
                        }
                    }else{
                        //System.out.println("Please Check you input");
                        String msg = "กรอกข้อมูลไม่ครบถ้วน \n-ชื่อวิชาไม่ถึง 5 ตัวอักษร\n-ไม่ได้เลือกเกรด";
                        JOptionPane.showMessageDialog(null,msg, "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }

                }
            });

        JLabel addIcon = new JLabel(new ImageIcon("addIcon.png"));
        addIcon.setBounds(10,10,100,43);
        JLabel addIcon2 = new JLabel(new ImageIcon("addIcon2.png"));
        addIcon2.setBounds(10,10,100,43);
        JLabel delIcon = new JLabel(new ImageIcon("delIcon.png"));
        delIcon.setBounds(120,10,100,43);
        JLabel delIcon2 = new JLabel(new ImageIcon("delIcon2.png"));
        delIcon2.setBounds(120,10,100,43);

        addIcon.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    AddCourse();
                    if(couse_cnt == (maxCourse-1))
                    {
                        Panel.remove(addIcon);
                        Panel.add(addIcon2);
                    }
                    if(couse_cnt == 1){
                        Panel.remove(delIcon2);
                        Panel.add(delIcon);
                    }

                    revalidate();//fix arrow problem
                    repaint();
                    //System.out.println(couse_cnt);
                }
            });

        delIcon.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {

                    if(couse_cnt == (maxCourse-1)){
                        Panel.remove(addIcon2);
                        Panel.add(addIcon);
                    }
                    if(couse_cnt != 1){
                        delCourse();
                    }else{
                        delCourse();
                        Panel.remove(delIcon);
                        Panel.add(delIcon2);
                    }

                    revalidate();//fix arrow problem
                    repaint();
                    //System.out.println(couse_cnt);
                }
            });

        contentPane.add(xIcon);
        contentPane.add(mIcon);

        contentPane.add(logo);  

        Panel = new JPanel(null);
        Panel.setBorder(BorderFactory.createEtchedBorder(1));
        Panel.setBounds(0,200,800,400);
        Panel.setBackground(new Color(204,204,255));
        Panel.setForeground(new Color(0,0,0));
        Panel.setEnabled(true);
        Panel.setFont(new Font("sansserif",0,12));
        Panel.setVisible(true);

        //adding components to contentPane panel
        Panel.add(addIcon);
        Panel.add(delIcon2);
        Panel.add(nextBtn);
        contentPane.add(Panel);
        AddCourse();
        //contentPane.remove(Panel);
        //adding panel to JFrame and seting of window position and close operation
        this.add(contentPane);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.pack();
        this.setVisible(true);
    }

    public void AddCourse(){
        int space_line = 70;
        couse_cnt++;
        if(couse_cnt != 0)
            space_line += (46*couse_cnt);

        //Label #No
        Course[couse_cnt] = new JLabel();        
        Course[couse_cnt].setBounds(120,space_line,100,24);
        Course[couse_cnt].setBackground(new Color(0,0,0));
        Course[couse_cnt].setForeground(new Color(0,0,153));
        Course[couse_cnt].setEnabled(true);
        Course[couse_cnt].setFont(new Font("SansSerif",0,24));
        Course[couse_cnt].setText("วิชาที่ "+(couse_cnt+1));
        Course[couse_cnt].setVisible(true);

        CourseName[couse_cnt] = new JTextField();
        CourseName[couse_cnt].setBounds(220,space_line,90,26);
        CourseName[couse_cnt].setBackground(new Color(255,255,255));
        CourseName[couse_cnt].setForeground(new Color(0,0,0));
        CourseName[couse_cnt].setEnabled(true);
        CourseName[couse_cnt].setFont(new Font("sansserif",0,12));
        //CourseName[couse_cnt].setText("CourseName");
        CourseName[couse_cnt].setVisible(true);

        String[] Grade = {" ","A", "B+", "B", "C+", "C" ,"D+" ,"D" ,"F"};
        gradeBox[couse_cnt] = new JComboBox();
        gradeBox[couse_cnt].setModel(new DefaultComboBoxModel(Grade));
        gradeBox[couse_cnt].setBounds(330,space_line,70,26);
        gradeBox[couse_cnt].setBackground(new Color(214,217,223));
        gradeBox[couse_cnt].setForeground(new Color(0,0,0));
        gradeBox[couse_cnt].setEnabled(true);
        gradeBox[couse_cnt].setFont(new Font("sansserif",0,24));
        gradeBox[couse_cnt].setVisible(true);

        Panel.add(Course[couse_cnt]);
        Panel.add(CourseName[couse_cnt]);
        Panel.add(gradeBox[couse_cnt]);

    }

    private void reportPanel(){
        Panel2 = new JPanel(null);
        Panel2.setBorder(BorderFactory.createEtchedBorder(1));
        Panel2.setBounds(0,200,800,400);
        Panel2.setBackground(new Color(204,204,255));
        Panel2.setForeground(new Color(0,0,0));
        Panel2.setEnabled(true);
        Panel2.setFont(new Font("sansserif",0,12));
        Panel2.setVisible(true);
        contentPane.add(Panel2);
        addResult();
    }

    private void addResult(){
        int space_line = 30;
        double gpa;

        gpa = Math.floor((totalGrade/totalCredit)*100)/100;
        for(int i = 0;i<=couse_cnt;i++){
            if(i != 0)
                space_line += 46;
            JLabel CourseR = new JLabel();  
            JLabel CourseC = new JLabel(); 
            JLabel CourseG = new JLabel(); 
            CourseR.setBounds(15,space_line,500,30);
            CourseR.setBackground(new Color(0,0,0));
            CourseR.setForeground(new Color(0,0,153));
            CourseR.setEnabled(true);
            CourseR.setFont(new Font("SansSerif",0,16));
            CourseR.setText(courseCode[i]+"      "+courseName[i]);
            CourseR.setVisible(true);
            CourseC.setBounds(520,space_line,30,30);
            CourseC.setBackground(new Color(0,0,0));
            CourseC.setForeground(new Color(0,0,153));
            CourseC.setEnabled(true);
            CourseC.setFont(new Font("SansSerif",0,16));
            CourseC.setText(gradeBox[i].getSelectedItem().toString());
            CourseG.setBounds(580,300,300,100);
            CourseG.setBackground(new Color(0,0,0));
            CourseG.setForeground(new Color(250,0,0));
            CourseG.setEnabled(true);
            CourseG.setFont(new Font("SansSerif",0,40));
            CourseG.setText("GPA : "+gpa);

            Panel2.add(CourseC);
            Panel2.add(CourseR);
            Panel2.add(CourseG);

        }
        JLabel reBtn = new JLabel(new ImageIcon("reBtn.png"));
        reBtn.setBounds(200,space_line+46,100,43);
        reBtn.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    main Main = new main();
                    dispose();
                    Main.run();
                }
            });
        Panel2.add(reBtn);
    }

    private void delCourse()
    {
        Panel.remove(Course[couse_cnt]);
        Panel.remove(CourseName[couse_cnt]);
        Panel.remove(gradeBox[couse_cnt]); 
        couse_cnt--;
    }

    private void calculateGrade(File myDB){
        Course course = new Course(myDB);
        int i;
        for(i=0;i<=couse_cnt;i++){
            courseCode[i] = toUpper(CourseName[i].getText());
            if(course.searchCode(courseCode[i])){            
                courseName[i] = course.getFullCode();
                courseCredit[i] = course.getCredit();
            }else{ courseName[i] = "Invalid Course Code";validate = false;}
            switch(gradeBox[i].getSelectedItem().toString()){
                case "A"  : courseGrade[i] = 4.00*courseCredit[i];
                break;
                case "B+"  : courseGrade[i] = 3.50*courseCredit[i];
                break;
                case "B"  : courseGrade[i] = 3.00*courseCredit[i];
                break;
                case "C+"  : courseGrade[i] = 2.50*courseCredit[i];
                break;
                case "C"  : courseGrade[i] = 2.00*courseCredit[i];
                break;
                case "D+"  : courseGrade[i] = 1.50*courseCredit[i];
                break;
                case "D"  : courseGrade[i] = 1.00*courseCredit[i];
                break;
                case "F"  : courseGrade[i] = 0.00;
                break;
            }
            totalCredit += courseCredit[i];
            totalGrade += courseGrade[i];
            //System.out.println(courseCode[i]+" is "+courseName[i]+" Grade = "+courseGrade[i]);
        }

    }

    private String toUpper(String Str){
        String str = new String(Str);
        StringBuilder  temp = new StringBuilder (str);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLetter(c)) {
                temp.setCharAt(i, Character.toUpperCase(c));
            }else{
                temp.setCharAt(i, c);
            }
        }
        return temp.toString();
    }

    private boolean checkField(){
        int i;
        boolean c = true;
        String textBox = new String();
        String gradeBoxC = new String();
        for(i=0;i<=couse_cnt;i++){
            textBox = CourseName[i].getText();
            gradeBoxC = gradeBox[i].getSelectedItem().toString();
            if(textBox.length()< 4){
                c = false;
            }
            if(gradeBoxC == " "){
                c = false;
            }

        }
        if(c){return true;}else{return false;}
    }

}