package fr.mbds.grails

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(value=["hasRole('ROLE_ADMIN')"])
class AnnonceController {

    AnnonceService annonceService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond annonceService.list(params), model:[annonceCount: annonceService.count()]
    }

    def show(Long id) {
        respond annonceService.get(id)
    }

    def create() {
        respond new Annonce(params)
    }

    def save(Annonce annonce) {
        if (annonce == null) {
            notFound()
            return
        }

        try {
            annonceService.save(annonce)
        } catch (ValidationException e) {
            respond annonce.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'annonce.label', default: 'Annonce'), annonce.id])
                redirect annonce
            }
            '*' { respond annonce, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond annonceService.get(id)
    }

    def modifillu()
    {
        def annonce = Annonce.get(params.id)
        render: "dddd"

    }

    def update() {
        def annonce = Annonce.get(params.id)
        if (annonce == null) {
            notFound()
            return
        }
        annonce.title = params.title
        annonce.description = params.description
        annonce.price = Float.parseFloat(params.price)
        def f = request.getFile('file')
        def tempsactuel =new Date().getTime()

        def filename = f.originalFilename
        if(filename)
        {
            File fileDest = new File("${grailsApplication.config.illustration.path+tempsactuel+filename}")
            f.transferTo(fileDest)

            annonce.addToIllustrations(new Illustration(filename: "${tempsactuel+filename}"))
        }





        /**
         * 1. On r??cup??re le fichier depuis la requ??te (request)
         * 2. On sauvegarde le fichier sur le systeme de fichier local (sur le disque)
         *      - On g??n??re un nom de fichier al??atoire et long
         *      - On v??rifie que le fichier n'est pas d??j?? pr??sent sur le syst??me de fichier
         *      - Si tout est bon on peut sauvegarder le fichier re??u (request) sur le disque
         * 3. On cr??e une instance d'Illustration associ??e ?? l'image que l'on a sauvegard??
         * 4. On associe l'Illustration ?? l'annonce courante
         */

        try {
            annonceService.save(annonce)
        } catch (ValidationException e) {
            respond annonce.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'annonce.label', default: 'Annonce'), annonce.id])
                redirect annonce
            }
            '*'{ respond annonce, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        annonceService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'annonce.label', default: 'Annonce'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'annonce.label', default: 'Annonce'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
