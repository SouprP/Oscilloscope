package me.souprpk.window;

import me.souprpk.enums.WaveType;
import me.souprpk.utils.Line;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.awt.*;
import java.nio.*;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    private long glfwWindow;
    private int width, height;
    private String title;

    private static Window window = null;

    public Window(){
        this.width = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.7); // 1200
        this.height = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.6); // 800
        this.title = "Oscilloscope";
    }

    public static Window getWindow(){
        if(window == null)
            window = new Window();

        return window;
    }

    public void run(){
        System.out.println("LWJGL started! v" + Version.getVersion());

        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and free error callbacks
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init(){
        // Set up error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if(!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW.");

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if(glfwWindow == NULL)
            throw new IllegalStateException("Failed to create the GLFW window.");

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // Make sure you can use bindings
        GL.createCapabilities();

        //glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        //glClear(GL_COLOR_BUFFER_BIT);


    }

    public void loop(){
        while(!glfwWindowShouldClose(glfwWindow)){
            // Poll events
            glfwPollEvents();

            glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glColor3f(1f, 0f, 0f);
            glBegin(GL_LINE_STRIP);
            WaveType wave = WaveType.SAWTOOTH;
            float f = 0.1f;
            float A = 0.5f; // Działka U
            float y = 0f;
            float T = 1f; // Działka T
            for (float x = -1f; x <= 1f; x += 0.001) {
                switch (wave){
                    case SINE:
                        y = (float) (A*sin((f*x)/T));
                        break;
                    case SAWTOOTH:
                        y = (float) (A*(x/f - Math.floor(x/f))/T);
                        break;
                    case TRIANGLE:
                        y = (float) (A*2*Math.abs(2*(f*x/T - Math.floor(f*x/T + 0.5))) - 1);
                        break;
                    default:
                        break;
                }
                //y = (float) Math.sqrt(1 - Math.pow(x, 2));
                //System.out.println(y);
                glVertex2f(x, y);
                //glVertex2f(x, -y);
            }
            glEnd();

            glfwSwapBuffers(glfwWindow);
        }
    }
}
