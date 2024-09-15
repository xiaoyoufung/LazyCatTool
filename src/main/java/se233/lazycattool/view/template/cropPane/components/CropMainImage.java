package se233.lazycattool.view.template.cropPane.components;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.CropImage;
import se233.lazycattool.view.template.components.ImageViewURL;

public class CropMainImage extends Pane {
    private double startX, startY;
    private boolean draggingTopLeft, draggingTopRight, draggingBottomLeft, draggingBottomRight, draggingCropArea;
    private double cropX = 84, cropY = 56, cropWidth = 330, cropHeight = 205, paneWidth = 512, paneHeight = 312;

    public CropMainImage(String url){
        this.setPrefSize(512, 312);

        String imgURL1 = "file:/Users/xiaoyoufung/Desktop/test-photo/blue_dusk.png";

        // User current Image.
        ImageView imageView = new ImageView(new Image("file:" + url));

        // Background Image.
        ImageView backgroundImage = new ImageViewURL("assets/images/imageWrap.jpg");

        backgroundImage.setFitWidth(paneWidth);
        backgroundImage.setFitHeight(paneHeight);
        backgroundImage.setPreserveRatio(true);
        imageView.setFitWidth(paneWidth);
        imageView.setFitHeight(paneHeight);
        imageView.setPreserveRatio(true);

        Canvas canvas = new Canvas(paneWidth, paneHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        draw(gc);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            startX = e.getX();
            startY = e.getY();
            draggingTopLeft = isInsideSquare(startX, startY, cropX, cropY);
            draggingTopRight = isInsideSquare(startX, startY, cropX + cropWidth, cropY);
            draggingBottomLeft = isInsideSquare(startX, startY, cropX, cropY + cropHeight);
            draggingBottomRight = isInsideSquare(startX, startY, cropX + cropWidth, cropY + cropHeight);
            draggingCropArea = isInsideCropArea(startX, startY, cropX, cropY, cropWidth, cropHeight);
        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            double deltaX = e.getX() - startX;
            double deltaY = e.getY() - startY;

            if (draggingTopLeft) {
                if (cropX + deltaX >= 0 && cropY + deltaY >= 0 && cropWidth - deltaX >= 10 && cropHeight - deltaY >= 10) {
                    cropX += deltaX;
                    cropY += deltaY;
                    cropWidth -= deltaX;
                    cropHeight -= deltaY;
                }
            } else if (draggingTopRight) {
                if (cropY + deltaY >= 0 && cropX + cropWidth + deltaX <= canvas.getWidth() && cropWidth + deltaX >= 10 && cropHeight - deltaY >= 10) {
                    cropY += deltaY;
                    cropWidth += deltaX;
                    cropHeight -= deltaY;
                }
            } else if (draggingBottomLeft) {
                if (cropX + deltaX >= 0 && cropY + cropHeight + deltaY <= canvas.getHeight() && cropWidth - deltaX >= 10 && cropHeight + deltaY >= 10) {
                    cropX += deltaX;
                    cropWidth -= deltaX;
                    cropHeight += deltaY;
                }
            } else if (draggingBottomRight) {
                if (cropX + cropWidth + deltaX <= canvas.getWidth() && cropY + cropHeight + deltaY <= canvas.getHeight() && cropWidth + deltaX >= 10 && cropHeight + deltaY >= 10) {
                    cropWidth += deltaX;
                    cropHeight += deltaY;
                }
            } else if (draggingCropArea) {
                if (cropX + deltaX >= 0 && cropX + cropWidth + deltaX <= canvas.getWidth() && cropY + deltaY >= 0 && cropY + cropHeight + deltaY <= canvas.getHeight()) {
                    cropX += deltaX;
                    cropY += deltaY;
                }
            }

            startX = e.getX();
            startY = e.getY();

            draw(gc);
        });

        // clip a border
        Rectangle clip = new Rectangle(paneWidth, paneHeight);
        clip.setArcWidth(18);
        clip.setArcHeight(18);
        this.setClip(clip);
        //

        this.getChildren().addAll(backgroundImage, imageView, canvas);
    }

    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, paneWidth, paneHeight);

        // Draw the darkened background
        gc.setFill(Color.rgb(0, 0, 0, 0.375));
        gc.fillRect(0, 0, paneWidth, paneHeight);

        // Set the stroke width for the crop area border
        gc.setLineWidth(2);

        // Clear the crop area to make it transparent
        gc.clearRect(cropX, cropY, cropWidth, cropHeight);

        // Draw a border around the crop area
        gc.setStroke(Color.BLACK);
        gc.strokeRect(cropX, cropY, cropWidth, cropHeight);

        // Draw small squares at each corner and midpoint of the crop area border
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK); // Set the stroke color for the square borders
        int squareSize = 10; // Size of the small squares

        // Function to draw a square with a border
        drawSquareWithBorder(gc, cropX - squareSize / 2, cropY - squareSize / 2, squareSize);
        drawSquareWithBorder(gc, cropX + cropWidth - squareSize / 2, cropY - squareSize / 2, squareSize);
        drawSquareWithBorder(gc, cropX - squareSize / 2, cropY + cropHeight - squareSize / 2, squareSize);
        drawSquareWithBorder(gc, cropX + cropWidth - squareSize / 2, cropY + cropHeight - squareSize / 2, squareSize);

        // Print the crop area position and size
        System.out.println("Crop Area Position and Size:");
        System.out.println("X: " + cropX);
        System.out.println("Y: " + cropY);
        System.out.println("Width: " + cropWidth);
        System.out.println("Height: " + cropHeight);
    }

    private void drawSquareWithBorder(GraphicsContext gc, double x, double y, int size) {
        gc.fillRect(x, y, size, size); // Fill the square
        gc.strokeRect(x, y, size, size); // Draw the border
    }


    private boolean isInsideSquare(double x, double y, double squareX, double squareY) {
        int squareSize = 10;
        return x >= squareX - squareSize / 2 && x <= squareX + squareSize / 2 &&
                y >= squareY - squareSize / 2 && y <= squareY + squareSize / 2;
    }

    private boolean isInsideCropArea(double x, double y, double cropX, double cropY, double cropWidth, double cropHeight) {
        return x >= cropX && x <= cropX + cropWidth && y >= cropY && y <= cropY + cropHeight;
    }

    public void resetCropArea(){
        this.cropX = 84;
        this.cropY = 56;
        this.cropWidth = 330;
        this.cropHeight = 205;
        Launcher.refreshCropPane();
    }

    public CropImage getCroppedImage(){
        CropImage cropImage = new CropImage(cropX, cropY, cropWidth, cropHeight);
        return cropImage;
    }
}
