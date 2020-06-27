package com.dgdevelop.notificattion.model

class PlatziNotification private  constructor(
    private val title: String,
    private val description: String,
    private val id: String,
    private val descount: String
){
    fun getTitle(): String{
        return this.title
    }
    fun getDescription(): String{
        return this.description
    }
    fun getId(): String{
        return this.id
    }
    fun getDescount(): String{
        return this.descount
    }

    class Builder{
        var title: String = ""
        var description: String = ""
        var id: String = ""
        var descount: String = ""

        fun setTitle(newTitle: String): Builder = apply {
            this.title = newTitle
        }

        fun setDescription(newDescription: String): Builder = apply {
            this.description = newDescription
        }

        fun setId(newId: String): Builder = apply {
            this.id = newId
        }

        fun setDescount(newDescount: String): Builder = apply {
            this.descount = newDescount
        }

        fun build(): PlatziNotification = PlatziNotification(
            title,
            description,
            id,
            descount
        )

    }
}