package co.omisego.omgshop.pages.profile.caller

import co.omisego.omgshop.models.Credential
import co.omisego.omisego.model.APIError
import co.omisego.omisego.model.Logout
import co.omisego.omisego.model.OMGResponse
import co.omisego.omisego.model.User
import co.omisego.omisego.model.WalletList

/*
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 19/6/2018 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */
interface MyProfileCallerContract {
    interface Caller {
        val credential: Credential
        fun loadUser(authToken: String = credential.omisegoAuthenticationToken)
        fun loadWallets(authToken: String = credential.omisegoAuthenticationToken)
        fun logout(authToken: String = credential.omisegoAuthenticationToken)
    }

    interface Handler {
        fun handleLogoutSuccess(response: OMGResponse<Logout>)
        fun handleLogoutFailed(error: OMGResponse<APIError>)
        fun handleLoadUserSuccess(response: OMGResponse<User>)
        fun handleLoadUserFailed(error: OMGResponse<APIError>)
        fun handleLoadWalletSuccess(response: OMGResponse<WalletList>)
        fun handleLoadWalletFailed(error: OMGResponse<APIError>)
    }
}