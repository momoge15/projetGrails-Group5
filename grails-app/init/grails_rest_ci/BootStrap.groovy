package grails_rest_ci

import fr.mbds.grails.Annonce
import fr.mbds.grails.Illustration
import fr.mbds.grails.Role
import fr.mbds.grails.User
import fr.mbds.grails.UserRole

class BootStrap {

    def init = { servletContext ->

        // On crée les roles que l'on va utiliser ensuite
        def adminRole = new Role(authority: "ROLE_ADMIN").save()
        def modoRole = new Role(authority: "ROLE_MOD").save()
        def userRole = new Role(authority: "ROLE_USER").save()

        // On crée un administrateur et un modérateur
        def adminUser = new User(username: "admin", password: "password").save()
        def moderateur = new User(username: "moderateur", password: "password").save()

        // On associe les comptes créés au dessus à leur roles
        UserRole.create(adminUser, adminRole, true)
        UserRole.create(moderateur, modoRole, true)

        // On itère sur une liste de 5 "String"
        ["Alice","Bob","Charlie","Danny","Etienne"].each {
            String username ->
                // On crée les utilisateurs associés à ces Strings
                def userInstance = new User(username: username, password: "password")
                userInstance.save()
                // On associe l'utilisateur créé au role "Utilisateur"
                UserRole.create(userInstance, userRole, true)
                // Pour chaque utilisateur on itère 5 fois
                (1..5).each {
                    Integer index ->
                        // A chaque itération, on crée une nouvelle annonce
                        def annonceInstance =
                                new Annonce(
                                        title: "title $index",
                                        description: "description $index",
                                        price: 100*index)
                        // On itère 5 fois par annonce
                        (1..5).each {
                                // On crée et on associe à l'annonce, une nouvelle illustration
                                annonceInstance.addToIllustrations(new Illustration(filename: "grails.svg"))
                        }
                        // On associe l'annonce créée précédement à l'utilisateur
                        userInstance.addToAnnonces(annonceInstance)
                }
                // On sauvegarde l'utilisateur qui va sauvegarder ses fils ( annonces ) qui iront sauvegarder leurs fils (illustrations)
                userInstance.save()
        }

    }
    def destroy = {
    }
}
