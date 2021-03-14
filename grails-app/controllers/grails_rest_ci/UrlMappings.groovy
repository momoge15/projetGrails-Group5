package grails_rest_ci

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        // localhost:8080/test/1.json
        // $id = 1
        // $format = json
        "/test"(controller: "test", action: "index")

        "/"(view:"/index")
        "500"(view:'/error')

        // localhost:8080/404
        "404"(view:'/notFound')
    }
}
