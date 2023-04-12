package me.souprpk.utils;

import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

public class Line {

    /*
    private float start_x;
    private float end_x;
    private float start_y;
    private float end_y;
    */
    public Line(float start_x, float start_y, float end_x, float end_y){
        drawLine(start_x, start_y, end_x, end_y);
    }

    private void drawLine(float start_x, float start_y, float end_x, float end_y){
        glPointSize(10f);
        glBegin(GL_POINTS);
        glColor3f(1f, 0f, 0f);

        int samples = 100;
        float dx = (end_x - start_x) / samples;
        float dy = (end_y - start_y) / samples;

        for( int i = 0; i < samples; i++ )
        {
            glVertex2f( start_x + i * dx, start_y + i * dy );
        }

        glEnd();//end drawing of points
    }
}
