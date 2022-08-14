import java.awt.image.BufferedImage;

public class filters
{
    int width;
    int height;

    filters(BufferedImage img)
    {
        width = img.getWidth();
        height = img.getHeight();
    }
    public void greyscale(BufferedImage img)
    {
        for(int j = 0 ; j < height ; j++)
        {
            for(int i = 0 ; i < width ; i++)
            {
                //getting int pixel value at (i,j)
                int pixel = img.getRGB(i,j);

                //we need to convert the int value to binary to extract the color bitwise
                // AAAAAAAA|RRRRRRRR|GGGGGGGG|BBBBBBBB
                //31------24--------16-------8-------0

                //0xff
                // 00000000 00000000 00000000 11111111

                int a = (pixel >> 24) & 0xff;       //right shift then bitmasking
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;               //bitmasking

                int avr = (r + g + b) / 3;

                //left shifting the bits and then adding
                pixel = a << 24 | (avr << 16) | (avr << 8) | avr;

                img.setRGB(i,j,pixel);
            }
        }
    }

    public void negative(BufferedImage img)
    {
        for(int j = 0 ; j < height ; j++)
        {
            for(int i = 0 ; i < width ; i++)
            {
                int pixel = img.getRGB(i,j);

                int a = (pixel >> 24) & 0xff;
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;

                //invert the color of each pixel, dark becomes light and also the colors are inverted.

                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                pixel = a << 24 | (r << 16) | (g << 8) | b;

                img.setRGB(i,j,pixel);
            }
        }
    }

    public void sepia(BufferedImage img )
    {
        for(int j = 0 ; j < height ; j++)
        {
            for(int i = 0 ; i < width ; i++)
            {
                int pixel = img.getRGB(i,j);

                int a = (pixel >> 24) & 0xff;
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = (pixel) & 0xff;

                //sepia RGB color values
                int R = (int)((0.393 * r) + (0.769 * g) + (0.189 * b));
                int G = (int)((0.349 * r) + (0.686 * g) + (0.168 * b));
                int B = (int)((0.272 * r) + (0.534 * g) + (0.131 * b));

                if(R > 255)
                {
                    R = 255;
                }
                if(G > 255)
                {
                    G = 255;
                }
                if(B > 255)
                {
                    B = 255;
                }

                pixel = a << 24 | R << 16 | G << 8 | B;

                img.setRGB(i,j,pixel);
            }
        }
    }

    public void reverse(BufferedImage img, int x)
    {
        //Reversing or Flipping the image vertically
        if(x == 1)
        {
            for (int j = 0; j < height / 2; j++)
            {
                for (int i = 0; i < (width); i++)
                {
                    int pixel1 = img.getRGB(i, j);
                    int pixel2 = img.getRGB(i, height - 1 - j);

                    img.setRGB(i, j, pixel2);
                    img.setRGB(i, height - 1 - j, pixel1);
                }
            }
        }
        //Reversing or Flipping the image horizontally
        else if (x == 2)
        {
            for (int j = 0; j < height; j++)
            {
                for (int i = 0; i < (width/2); i++)
                {
                    int pixel1 = img.getRGB(i, j);
                    int pixel2 = img.getRGB(width - 1- i, j);

                    img.setRGB(i, j, pixel2);
                    img.setRGB(width - 1 - i , j, pixel1);
                }
            }
        }
    }
    public void contrast(BufferedImage img)
    {
        for(int j = 0 ; j < height ; j++)
        {
            for(int i = 0 ; i < width ; i++)
            {
                int pixel = img.getRGB(i,j);

                int a = (pixel >> 24) & 0xff;
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;

                //enhancing the red color values making the bright shade brighter and dark shade darker
                if(r > 128 && r < 212)
                {
                    r = (int)(r * 1.2);
                }
                else if(r < 128)
                {
                    r = (int)(r / 1.2);
                }

                //enhancing the green color values making the bright shade brighter and dark shade darker
                if(g > 128 && g < 212)
                {
                    g = (int)(g * 1.2);
                }
                else if(g < 128)
                {
                    g = (int)(g / 1.2);
                }

                //enhancing the blue color values making the bright shade brighter and dark shade darker
                if(b > 128 && b < 212)
                {
                    b = (int)(b * 1.2);
                }
                else if(b < 128)
                {
                    b = (int)(b / 1.2);
                }

                pixel = a << 24 | (r << 16) | (g << 8) | b;

                img.setRGB(i,j,pixel);
            }
        }
    }

    public void blur(BufferedImage img)
    {

        for(int j = 0 ; j < height ; j++)
        {
            for(int i = 0 ; i < width ; i++)
            {
                int pixel, a = 0, r, g, b;
                int R = 0;
                int G = 0;
                int B = 0;
                int t = 0;

                //checking the 3x3 matrix around the pixel
                for(int k = -2 ; k < 3 ; k++)
                {
                    for(int l = -2 ; l < 3 ; l++)
                    {
                        //checking if pixel is outside row
                        if((i + k) < 0 || (i + k) >= width)
                        {
                            //if yes then continue
                            continue;
                        }
                        //checking if pixel is outside column
                        if ((j + l) < 0 || (j + l) >= height)
                        {
                            //if yes then continue
                            continue;
                        }

                        pixel = img.getRGB(i+k,j+l);

                        //extract RGB values from the pixel
                        a = (pixel >> 24) & 0xff;
                        r = (pixel >> 16) & 0xff;
                        g = (pixel >> 8) & 0xff;
                        b = pixel & 0xff;

                        //else add to calculate the avg
                        R += r;
                        G += g;
                        B += b;
                        t++;
                    }
                }

                pixel = a << 24 | (R/t) << 16 | (G/t) << 8 | (B/t);
                img.setRGB(i,j,pixel);



            }
        }
    }

}
