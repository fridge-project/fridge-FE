package com.example.alne.Network

object
API {


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
    const val INGREDIENTS_ALL = "/api/ingredients"
    const val RECIPE_DETAIL = "/api/recipes/{recipe_code}"
    const val RECIPE_LIST = "/api/recipes"
    const val LIKE = "/api/like/{id}"
    const val LIKE_LIST = "/api/like"
    const val ADD_COMMENT = "/api/comment/{recipe_id}"
    const val DELETE_COMMENT = "/api/comment/{id}"
    const val UPDATE_COMMENT = "/api/comment/{id}"
    const val FAVORITE = "/api/favorite/{recipe_id}"
    const val FAVORITE_LIST = "/api/favorite"
    const val COMMENT_LIST = "/api/comment"






}