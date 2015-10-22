import com.torstenmuller.app._
import com.mongodb.casbah.Imports._
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {

    val mongoClient =  MongoClient()
    val mongoColl = mongoClient("doggies")("dogs")

    context.mount(new DoggieApi(mongoColl), "/dogs")
  }
}
