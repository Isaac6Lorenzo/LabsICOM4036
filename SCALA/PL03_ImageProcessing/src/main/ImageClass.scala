package main

import java.io.File
import java.awt.image.BufferedImage
import javax.imageio.ImageIO;
import java.io.IOException;


 
class ImageClass(var path: String, var input:BufferedImage = null,
      var pixels: Array[Int] = null) {
  
  def this(){
    this("", null)
  }
  
  def this(path: String){
    this("//foto adrress", null) 
  }
  
  
  def reader {
    val file = new File(path)
    input = ImageIO.read(file)
    println("Reading Complete")
    }
  
  def getHeight: Int = input.getHeight()
  def getWidth: Int = input.getWidth()
  def getInput: BufferedImage = input
  def getPath: String = path
  private def areapixels = getHeight * getWidth
  
  
  
  
  
  
  
  
  
  
  def findmedian{
    
  }
    

  
  
  
  
  
  
  
}