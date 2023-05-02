import java.awt.Color
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import java.util.Arrays
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._




class serialServer extends Actor{
  def receive : PartialFunction[Any, Unit]  = {
    case Serial(inputImage)  => {
      val startTime = System.currentTimeMillis()
      val filteredImage = serialFilter(inputImage)
      val endTime = System.currentTimeMillis() - startTime
      sender() ! (endTime, filteredImage)
    }

    def serialFilter(inputImage: BufferedImage) : BufferedImage = {
    
  }

}

class parallelServer extends Actor{

}


object MedianFilter extends App{
  val imagePath = scala.io.StdIn.readLine("Enter the path of the image you want to filter:  ")
  val inputImage = ImageIO.read(new File(imagePath))
  implicit val timeout: Timeout = 20.seconds
  
  // System 1
  val actorSystem = ActorSystem("ActorSystem")
  val serialActor = actorSystem.actorOf(Props[serialServer], name = "serialActor")
  val serialFuture = serialActor ? Serial(inputImage)

  // System 2
  val actorSystem2 = ActorSystem("ActorSystem")

  // Results
  // print outputs 
  actorSystem.terminate()
  actorSystem2.terminate()
}
