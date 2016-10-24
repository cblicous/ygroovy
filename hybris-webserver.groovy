import groovy.json.*
import com.sun.net.httpserver.*
import de.hybris.platform.servicelayer.user.*
import de.hybris.platform.core.model.user.* 
import de.hybris.platform.core.Registry

HttpServer server = HttpServer.create(new InetSocketAddress(8091),0);
server.createContext("/currentuser", new CurrentUserHandler(server:server));
server.createContext("/shutdown", new ShutdownHandler(server:server));
server.start();
log("Startup Server on 8091");

// to encapsule contenttype,status and this stuff
abstract class AdvancedHttpHandler implements HttpHandler {
 
  public void answer200(HttpExchange exchange, String response) throws IOException {
    exchange.responseHeaders['Content-Type'] = 'application/json'
	  exchange.sendResponseHeaders(200, response.length());
    exchange.getResponseBody().write(response.bytes);
    exchange.close();
  }
    
}


class CurrentUserHandler extends AdvancedHttpHandler {
    def server
    public void handle(HttpExchange exchange) throws IOException {
          def userService = Registry.getApplicationContext().getBean("userService", UserService.class);
      	  def userModel = userService.getCurrentUser()
           def builder = new JsonBuilder()
           builder {
               user userModel.getUid()
            
           }
           def response = builder.toString()
		       answer200(exchange,response);
       }
}
 
class ShutdownHandler extends AdvancedHttpHandler {
 
    def server
 
    public void handle(HttpExchange exchange) throws IOException {
      
        def builder = new JsonBuilder()
        builder {
            success true
            msg "Shutting down server..."
        }
        def response = builder.toString()
		    answer200(exchange,response);
        server.stop(5) 
    }
}


def log(String s) {
    println(s)
    Logger.getLogger("hybris-groovy-webserver").info(s)
}
