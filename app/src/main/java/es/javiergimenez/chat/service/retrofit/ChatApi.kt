package es.javiergimenez.chat.service.retrofit

import es.javiergimenez.chat.model.Chat
import es.javiergimenez.chat.model.Message
import es.javiergimenez.chat.model.User
import es.javiergimenez.chat.model.response.LoginResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*


interface ChatApi {


    @POST("/api/login")
    @FormUrlEncoded
    fun postLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<LoginResponse>

    @POST("/api/users/singup")
    @FormUrlEncoded
    fun postSingUp(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<User>

    @GET("/api/users")
    fun getUsers(
        @Header(ChatRetrofit.AUTHORIZATION) token: String
    ): Observable<List<User>>

    @GET("/api/chats")
    fun getChats(
        @Header(ChatRetrofit.AUTHORIZATION) token: String
    ): Observable<List<Chat>>

    @GET("/api/chats/{channelId}/messages")
    fun getMessages(
        @Header(ChatRetrofit.AUTHORIZATION) token: String,
        @Path("channelId") channelId: String
    ): Observable<Response<List<Message>>>

    @POST("/api/chats/{channelId}/messages")
    @FormUrlEncoded
    fun postMessage(
        @Header(ChatRetrofit.AUTHORIZATION) token: String,
        @Path("channelId") channelId: String,
        @Field("body") body: String
    ): Observable<Message>

}