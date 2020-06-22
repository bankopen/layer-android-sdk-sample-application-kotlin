package com.open.openpaymentdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.open.open_web_sdk.OpenPayment
import com.open.open_web_sdk.listener.PaymentStatusListener
import com.open.open_web_sdk.model.TransactionDetails
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PaymentStatusListener {

    private var mPaymentToken: String = ""
    private var mAccessKey: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonPay.setOnClickListener {
            responseText.text = "";
            setPaymentView(paymentToken = mPaymentToken, accessKey = mAccessKey)
        }

    }

    private fun setPaymentView(paymentToken: String, accessKey: String) {

        var openPayment: OpenPayment =
            OpenPayment.Builder()
                .with(this@MainActivity)
                .setPaymentToken(paymentToken)
                .setEnvironment(OpenPayment.Environment.SANDBOX)
                .setAccessKey(accessKey)
                .build()

        openPayment.setPaymentStatusListener(mListener = this@MainActivity)

        openPayment.startPayment()

    }

    override fun onError(message: String) {
        runOnUiThread {
            responseText.text = "onError: \n$message"
        }
    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails) {
        runOnUiThread {
            responseText.text = "On Transaction Completed:\n"
            responseText.append("\nPayment Id: " +transactionDetails.paymentId)
            responseText.append("\nPayment Token Id: " +transactionDetails.paymentTokenId)
            responseText.append("\nStatus: " +transactionDetails.status)
        }
    }

}