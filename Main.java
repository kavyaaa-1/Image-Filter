//Buffered Image class is subclass of Image class

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;


public class Main {
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\n\t\t\t------------WELCOME TO THE WORLD OF FILTERS------------\n\n");

        //creating a BufferImage object
        BufferedImage img = null;
        File f = null;

        int flag = 0;

        do {
            System.out.print("Enter the image file name: ");
            String fname = sc.nextLine();
            String f_name = "images/" + fname;

            try
            {
                f = new File(f_name);
                img = ImageIO.read(f);
                System.out.println("\nFile read successfully !!");
                flag = 1;
            }
            catch (IOException e)
            {
                System.out.println("Try Again..");
                System.out.println(e);
            }
        }while(flag == 0);

        do {

            flag = 1;

            System.out.println("\n\t\t\t\t-----------Select Filter-------------\n");
            System.out.println("\t\t\t1. Black and White");
            System.out.println("\t\t\t2. Negative Image");
            System.out.println("\t\t\t3. Sepia");
            System.out.println("\t\t\t4. Reverse");
            System.out.println("\t\t\t5. Constrast");
            System.out.println("\t\t\t6. Blur\n");

            System.out.print("\t\t\tEnter the filter number you want: ");
            int n = sc.nextInt();
            sc.nextLine();

            //creating an object of filters class
            filters filter = new filters(img);

            if (n == 1)
            {
                filter.greyscale(img);
            }
            else if (n == 2)
            {
                filter.negative(img);
            }
            else if (n == 3)
            {
                filter.sepia(img);
            }
            else if (n == 4)
            {
                System.out.println("\t\tIn which direction would you like to flip your image?");
                System.out.println("\t\t\t1. Vertically");
                System.out.println("\t\t\t2. Horizontally");

                System.out.print("\t\t\tEnter:");
                int x = sc.nextInt();
                sc.nextLine();

                filter.reverse(img, x);
            }
            else if(n == 5)
            {
                filter.contrast(img);
            }
            else if (n == 6)
            {
                filter.blur(img);
            }
            else
            {
                System.out.println("\tEnter a valid filter!!");
                flag = 0;
            }

        }while(flag == 0);

        System.out.print("\nEnter the final image file name: ");
        String output = sc.nextLine();
        String output_file = "C:/Users/gupta/IdeaProjects/FinalProject/images/" + output;


        try
        {
            f = new File(output_file);
            ImageIO.write(img,"jpg",f);
            System.out.println("\nNew file created successfully !!");
        }
        catch (IOException e)
        {
            System.out.println(e);
        }



    }
}