package fr.mbds.grails

import grails.rest.Resource


class Annonce {

    String title
    String description
    Float price
    Date dateCreated
    Date lastUpdated

    static hasMany = [illustrations: Illustration]

    static belongsTo = [author: User]

    static constraints = {
        title nullable: false, blank: false
        description nullable: false, blank: false
        price nullable: false, min: 0F
        illustrations nullable: true
    }

    static mapping = {
        description type: 'text'
        autoTimestamp true
    }
}
