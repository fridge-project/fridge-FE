package com.example.alne.Network

object API {


    //Auth
    const val LOGIN = "/api/auth/login"
    const val SIGNUP = "/api/auth/join"
    const val LOGOUT = "/api/auth/logout"
    const val CHANGEPASSWORD = "/api/auth/change-password"
    const val QUIT = "/api/auth/delete-account"


    //Fridge
    const val  FRIDGE = "/api/fridge"
    const val  FRIDGE_DELETE = "/api/fridge/{id}"
    const val  FRIDGE_UPDATE = "/api/fridge/{id}"

    //Recipe
    const val RECIPE_LIST = "/api/recipes"
    const val LIKE = "/api/like/{id}"
    const val LIKE_LIST = "/api/like"
    const val RECIPE_PROECSS = "/api/recipes/{recipe_code}"
    const val ADD_COMMENT = "/api/comment/{recipe_id}"
    const val DELETE_COMMENT = "/api/comment/{id}"






}