package practice;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
public class SharinganJFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2926930666301985832L;
	//设置中心坐标，个人喜欢一种心点作为物体的坐标，物理里面成为质点
    int centerX = 1024/2;
    int centerY = 768/2;
    //外圆半径，写轮眼转动所依赖的红线
    double extendCircleSemi = 25;
    //写轮眼的旋转部分有一个固定的大小的圆，刚开始设计的时候是定义为白色的，后来改为了红色，好看 ^_^
    double whiteOfSharingan = extendCircleSemi/3;
    //在上面的红色的圆旁边有一个随着旋转角度变大而变大的圆，其半径为miniCircleSemi
    double miniCircleSemi = 0;
    //这里用到了绘制太极时的思路，这个圆和上面的两个圆(whiteOfSharingan和miniCircleSemi)外切
    double sharinganSemi = 0;
    //旋转度角范围的变量，您可以通过观察旋转的循环角度是120*2
    int angleCircle = 0;
    //小圆(miniCircleSemi)和白圆(whiteOfSharingan)在外圆(extendCircleSemi)上的角度
    double angleOfWhiteMini = 0;
    //写轮眼沟玉圆(sharinganSemi)和白圆(whiteOfSharingan)在外圆(extendCircleSemi)的角度
    double angleOfwhiteShar = 0;
    //用于判断圆是否到达极限位置，还句话说就是是否到达了正确的角度，进行下一次循环
    boolean flag = true;
    //沟玉球心到弦(whiteOfSharingan圆中心和miniCircleSemi圆中心所连成的线)中心距离；
    double distansOfWhiteShar =0;
    //沟玉球心到中心点的距离，这一步主要是为了计算坐标
    double distansOfSharSemi = 0;
    //在绘图过程中，由于计算机计算开方、三角函数、反三角等造成了误差，这是一个补偿值，但要注意真正补偿的
    //误差为这个误差的angleErr*2/3,因为这个值是本人通过理想设置临界状态得到的值。
    double angleErr = 0.02500260489936114;
    //沟玉球(whiteOfSharingan)中的黑球半径，这个值是动态的
    int blackOfWhiteSemi = 0;
    //中心眼睛所占的长度
    int eyeLength = 300;
    //眼睛幅值
    int amplitude = 50;
                                     
    public SharinganJFrame() {
        this.setTitle("Sharingan");
        this.setSize(centerX*2, centerY*2);
        this.getContentPane().setBackground(Color.black);// 这里可能有BUG 设置颜色后面代码为(Color.black);大家自己手动修改一下。
        startRun();
        this.setVisible(true);
    }
                                     
    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        //绘制一个眼睛作为背景
        graphics.setColor(Color.red);
        for (int i = 0; i < eyeLength; i++) {
            graphics.drawLine(    centerX-eyeLength/2+i,
                                centerY-(int)(Math.sin(Math.PI*i/eyeLength)*amplitude),
                                centerX-eyeLength/2+i,
                                centerY+(int)(Math.sin(Math.PI*i/eyeLength)*amplitude));
        }
        //在眼睛的中心绘制一个黑色的圆
        graphics.setColor(Color.black);
        graphics.fillOval(    centerX-90/2,
                            centerY-90/2,
                            90,
                            90);
        //在眼睛的中心绘制一个白色的圆
        graphics.setColor(Color.white);
        graphics.fillOval(    centerX-60/2/2/2,
                            centerY-60/2/2/2,
                            60/2/2,
                            60/2/2);
        //用于绘制3个不同角度、在不断变化、不同位置的太极图，图是有顺序的
        for (int i = 0; i < 3; i++) {
            //绘制写轮眼球(sharinganSemi)
            graphics.setColor(Color.red);
            graphics.fillArc(    (int)(centerX-sharinganSemi+Math.cos(Math.PI*(angleCircle+90+(i*120))/180-angleOfWhiteMini/2+angleOfwhiteShar+angleErr*2/3)*distansOfSharSemi),
                                (int)(centerY-sharinganSemi-Math.sin(Math.PI*(angleCircle+90+(i*120))/180-angleOfWhiteMini/2+angleOfwhiteShar+angleErr*2/3)*distansOfSharSemi),
                                (int)(sharinganSemi*2),
                                (int)(sharinganSemi*2),angleCircle+i*120,180);
            //绘制写轮眼固定圆(whiteOfSharingan)
            graphics.setColor(Color.red);
            graphics.fillOval(    (int)(centerX-whiteOfSharingan+Math.cos(Math.PI*(angleCircle+90+(i*120))/180)*extendCircleSemi),
                                (int)(centerY-whiteOfSharingan-Math.sin(Math.PI*(angleCircle+90+(i*120))/180)*extendCircleSemi),
                                (int)(whiteOfSharingan*2),
                                (int)(whiteOfSharingan*2));
            //绘制写轮眼黑球(miniCircleSemi)
            graphics.setColor(Color.black);
            graphics.fillOval(    (int)(centerX-miniCircleSemi+Math.cos(Math.PI*(angleCircle+90+(i*120))/180-angleOfWhiteMini)*extendCircleSemi),
                                (int)(centerY-miniCircleSemi-Math.sin(Math.PI*(angleCircle+90+(i*120))/180-angleOfWhiteMini)*extendCircleSemi),
                                (int)(miniCircleSemi*2),
                                (int)(miniCircleSemi*2));
            //绘制写轮眼固定球内黑球(blackOfWhiteSemi)
            graphics.setColor(Color.black);
            graphics.fillOval(    (int)(centerX-(blackOfWhiteSemi+0.0)/120*whiteOfSharingan/2+Math.cos(Math.PI*(angleCircle+90+(i*120))/180)*extendCircleSemi),
                                (int)(centerY-(blackOfWhiteSemi+0.0)/120*whiteOfSharingan/2-Math.sin(Math.PI*(angleCircle+90+(i*120))/180)*extendCircleSemi),
                                (int)((blackOfWhiteSemi+0.0)/120*whiteOfSharingan),
                                (int)((blackOfWhiteSemi+0.0)/120*whiteOfSharingan));
        }
        //绘制外圆
            graphics.setColor(Color.red);
            graphics.drawOval(    (int)(centerX-extendCircleSemi),
                                (int)(centerY-extendCircleSemi),
                                (int)(extendCircleSemi)*2,
                                (int)(extendCircleSemi)*2);
    }
    public void startRun() {
        new Thread(){
            public void run() {
                while(true){
                    if (flag) {
                        angleCircle += 2 ;
                        //沟玉白球中的黑球半径
                        blackOfWhiteSemi = angleCircle;
                        //System.out.println(angleCircle);
                        //根据目前写轮眼沟玉球转过角度来确定mini小球目前的对应的半径
                        miniCircleSemi = (angleCircle+0.0)/120*whiteOfSharingan;
                        //System.out.println(miniCircleSemi);
                        //沟玉球半径
                        sharinganSemi = miniCircleSemi+whiteOfSharingan;
                    //System.out.println(sharinganSemi);
                        //由于白球和mini小球都是在外圆上，所以通过弦对应的角度来求的小圆落后于白球的角度
                        angleOfWhiteMini = Math.asin(sharinganSemi/2/extendCircleSemi)*2;
                        //System.out.println(angleOfWhiteMini);
                        //沟玉球心到白球中心距离
                        distansOfWhiteShar = (whiteOfSharingan-miniCircleSemi)/2;
                        //沟玉球心到中心点的距离
                        distansOfSharSemi = Math.sqrt(    extendCircleSemi*extendCircleSemi
                                                        -((whiteOfSharingan+miniCircleSemi)/2)*((whiteOfSharingan+miniCircleSemi)/2)
                                                        +((whiteOfSharingan-miniCircleSemi)/2)*((whiteOfSharingan-miniCircleSemi)/2));
                        //沟玉球心和白求在中心圆上所成的角度
                        //通过这里可以可以求出由于计算机计算产生的计算误差为（0.02500260489936114）
                    //System.out.println(distansOfSharSemi);
                        angleOfwhiteShar = Math.asin(distansOfWhiteShar/2/distansOfSharSemi);
                        //System.out.println(angleOfwhiteShar);
                        if (angleCircle == 120) {
                            flag = false;
                        }
                    }else {
                        angleCircle += 2;
                        //沟玉白球中的黑球半径
                        blackOfWhiteSemi = 240-angleCircle;
                        //根据目前写轮眼沟玉球转过角度来确定mini小球目前的对应的半径
                        miniCircleSemi = (240.0-angleCircle)/120*whiteOfSharingan;
                        //miniCircleSemi = (angleCircle+0.0)/120*whiteOfSharingan;
                        //沟玉球半径
                        sharinganSemi = miniCircleSemi+whiteOfSharingan;
                        //由于白球和mini小球都是在外圆上，所以通过弦对应的角度来求的小圆落后于白球的角度
                        angleOfWhiteMini = Math.asin(sharinganSemi/2/extendCircleSemi)*2;
                        //沟玉球心到白球中心距离
                        distansOfWhiteShar = (whiteOfSharingan-miniCircleSemi)/2;
                        //沟玉球心到中心点的距离
                        distansOfSharSemi = Math.sqrt(    extendCircleSemi*extendCircleSemi
                                                        -((whiteOfSharingan+miniCircleSemi)/2)*((whiteOfSharingan+miniCircleSemi)/2)
                                                        +((whiteOfSharingan-miniCircleSemi)/2)*((whiteOfSharingan-miniCircleSemi)/2));
                        //沟玉球心和白求在中心圆上所成的角度
                        angleOfwhiteShar = Math.asin(distansOfWhiteShar/2/distansOfSharSemi);
                        if (angleCircle == 240) {
                            angleCircle = 0;
                            flag = true;
                        }
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    repaint();       
                }
            };
        }.start();
    }
    public static void main(String[] args) {
        new SharinganJFrame();
    }
}