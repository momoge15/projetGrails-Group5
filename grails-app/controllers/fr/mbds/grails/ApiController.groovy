package fr.mbds.grails

import grails.converters.JSON
import grails.converters.XML
import grails.rest.RestfulController

class ApiController {
    AnnonceService annonceService




    def annonce() {
        switch(request.getMethod())
        {
            case "GET":
                if(!params.id)
                    return response.status = 400
                def annonceInstance = Annonce.get(params.id)
                if(!annonceInstance)
                    return  response.status = 404
                doSerialize(annonceInstance)
                break
            case "PUT":
                request.JSON.each { k,v ->
                    params[k] = v
                }

            // Get by either ID or hostname
                def hostInstance
                try {
                    hostInstance = Annonce.get(params.id)
                }
                catch(Exception E) {

                }

                try{
                    hostInstance.properties = params   // Set new properties
                    hostInstance.save(flush:true)
                    render "Modification effectuée avec succès"
                }
                catch (Exception e)
                {
                    render "Modification a échoué"
                }


                //respond hostInstance, [status: HttpStatus.OK]

                break
            case "PATCH":
            request.JSON.each { k,v ->
                params[k] = v
            }

            // Get by either ID or hostname
            def hostInstance
            try {
                hostInstance = Annonce.get(params.id)
            }
            catch(Exception E) {

            }

            try{
                hostInstance.properties = params   // Set new properties
                hostInstance.save(flush:true)
                render "Modification effectuée avec succès"
            }
            catch (Exception e)
            {
                render "Modification a échoué"
            }
                break
            case "DELETE":
                if(Annonce.get(params.id))
                    annonceService.delete(params.id)
                    render "Annonce "+params.id + " Supprimée"
                    break
            default:
                return response.status= 405
                break
        }
        return  response.status = 406

    }

    def annonces()
    {
        switch(request.getMethod())
        {
            case "GET":
                def allannonces = Annonce.getAll();
                doSerialize(allannonces)
                break
            case "POST":
                break
            default:
                return response.status= 405
                break
        }
        return  response.status = 406

    }
    def doSerialize(def instance){
        switch (request.getHeader("Accept"))
        {
            case 'json' :
            case 'text/json' :
            case 'application/json' :
                render instance as JSON
                break
            case 'xml' :
                render instance as XML
                break
            case 'json' :
                render instance as XML
                break
            case 'text/xml' :
            case 'application/xml' :
                render instance as XML
                break

        }
    }

    def annonceUpdate() {
        // Convert incoming JSON to params structure
        request.JSON.each { k,v ->
            params[k] = v
            render v
        }

        // Get by either ID or hostname
        def hostInstance
        annonceUput = annonceService.get(params.id)


        annonceUput.properties = params   // Set new properties
        annonceUput.save(flush:true)

        //respond annonceUput, [status: HttpStatus.OK]
    }
}
