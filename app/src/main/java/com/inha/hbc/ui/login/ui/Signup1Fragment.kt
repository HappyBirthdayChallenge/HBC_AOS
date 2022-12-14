package com.inha.hbc.ui.login.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.fragment.app.Fragment
import com.inha.hbc.R
import com.inha.hbc.data.remote.resp.CheckIdFailure
import com.inha.hbc.data.remote.resp.CheckIdSuccess
import com.inha.hbc.databinding.FragmentSignup1Binding
import com.inha.hbc.ui.login.view.CheckIdView
import com.inha.hbc.util.fragmentmanager.NormLoginFragmentManager
import com.inha.hbc.util.fragmentmanager.SignupFragmentManager
import com.inha.hbc.util.network.RetrofitService
import java.text.BreakIterator
import java.util.regex.Pattern

class Signup1Fragment: Fragment(), CheckIdView {
    lateinit var callback: OnBackPressedCallback
    lateinit var binding: FragmentSignup1Binding
    var id = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignup1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        setSpan()
    }

    fun initListener() {
        binding.ivSignup1Back.setOnClickListener {
            SignupFragmentManager.end()
        }
        binding.tvSignup1Next.setOnClickListener {
            id = binding.tieSignup1Id.text.toString()

            if (checkValid()) {
                binding.lavSignup1Loading.visibility = View.VISIBLE
                RetrofitService().checkId(id, this)
            }
            else {
                binding.tvSignup1Error.text = "???????????? 5~20?????? ?????? ???/?????????, ????????? ???????????? ????????? ?????????."
            }
        }

        binding.tieSignup1Id.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                id = binding.tieSignup1Id.text.toString()
                if (!checkValid()) {
                    binding.tvSignup1Error.text = "???????????? 5~20?????? ?????? ???/?????????, ????????? ???????????? ????????? ?????????."
                }
                else {
                    binding.tvSignup1Error.text = ""
                }
            }

        })
    }

    fun checkValid(): Boolean {
        val idPattern = "^[A-Za-z\\d]{5,20}\$"
        val pattern = Pattern.compile(idPattern)
        val matcher = pattern.matcher(id)
        return matcher.find()
    }

    fun setSpan() {
        val txt = "????????? ????????? ????????? ?????? ??? ???????????? ???????????????\n???????????? ????????? ???????????????."
        val span = SpannableString(txt)
        val click = object: ClickableSpan() {
            override fun onClick(p0: View) {
                val builder = AlertDialog.Builder(SignupFragmentManager.activity)
                builder.setTitle("???????????? ????????????")
                    .setMessage(privateStr)
                    .setPositiveButton("??????",
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog.dismiss()
                            })
                builder.show()
            }
        }
        span.apply {
            setSpan(click, 17, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(UnderlineSpan(), 17, 26, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

            binding.tvSignup1Subdescription.text = span
        binding.tvSignup1Subdescription.movementMethod = LinkMovementMethod.getInstance()
        binding.tvSignup1Subdescription.highlightColor = resources.getColor(R.color.transparent, null)
        }

    override fun onResponseSuccess(resp: CheckIdSuccess) {
        id = binding.tieSignup1Id.text.toString()
        SignupFragmentManager.signupData.id = id

        binding.lavSignup1Loading.visibility = View.GONE
        binding.tvSignup1Error.text = ""
        SignupFragmentManager.transaction(1, 2)
    }

    override fun onResponseFailure(resp: CheckIdSuccess) {
        binding.lavSignup1Loading.visibility = View.GONE
        binding.tvSignup1Error.text = "?????? ?????? ???????????????. ?????? ???????????? ??????????????????."
    }

    override fun onResponseFailure(resp: CheckIdFailure) {
    }

    override fun onResponseFailure(message: String) {
        binding.lavSignup1Loading.visibility = View.GONE
        binding.tvSignup1Error.text = "?????? ?????? ???????????????. ?????? ???????????? ??????????????????."
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                SignupFragmentManager.backPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }


    val privateStr = "< HBC >('https://www.notion.so/Happy-Birthday-Challenge-d4c'?????? 'HBC')???(???) ??????????????? ???????????? ???30?????? ?????? ??????????????? ??????????????? ???????????? ?????? ????????? ????????? ???????????? ???????????? ????????? ??? ????????? ?????? ????????? ????????? ?????? ???????????? ??????????????? ???????????????????????.\n" +
            "??? ??? ???????????? ??????????????? 2022??? 11??? 15????????? ???????????????.\n" +
            "\n" +
            "???1???(??????????????? ?????? ??????)\n" +
            "< HBC >('https://www.notion.so/Happy-Birthday-Challenge-d4c'?????? 'HBC')???(???) ????????? ????????? ????????? ??????????????? ???????????????. ???????????? ?????? ??????????????? ????????? ?????? ????????? ???????????? ???????????? ????????? ?????? ????????? ???????????? ???????????? ??????????????? ???????????? ???18?????? ?????? ????????? ????????? ?????? ??? ????????? ????????? ????????? ???????????????.\n" +
            "1. ???????????? ???????????? ??? ??????\n" +
            "?????? ???????????? ??????, ????????? ????????? ????????? ?????? ?????? ??????????????, ???????????? ??????????????, ????????? ???????????? ??????, ?????? ??????????????, ???????????? ???????????? ??????????????? ???????????????.\n" +
            "\n" +
            "2. ?????? ?????? ????????? ??????\n" +
            "????????? ??????, ????????? ??????, ??????????????? ??????, ??????????????? ???????????? ??????????????? ???????????????.\n" +
            "\n" +
            "3. ????????? ??? ???????????? ??????\n" +
            "?????? ?????????(??????) ?????? ??? ?????? ????????? ??????, ????????? ??? ????????? ?????? ?????? ??? ???????????? ?????? , ?????????????????? ????????? ?????? ????????? ?????? ??? ?????? ?????? , ???????????? ????????? ??????, ???????????? ?????? ?????? ????????? ????????? ????????? ?????? ?????? ?????? ???????????? ??????????????? ???????????????.\n" +
            "\n" +
            "\n" +
            "???2???(??????????????? ?????? ??? ?????? ??????)\n" +
            "??? < HBC >???(???) ????????? ?????? ???????????? ???????????????????? ?????? ????????????????????? ??????????????? ?????? ?????? ???????????? ???????????? ???????????????????? ????????? ??????????????? ???????????????????????.\n" +
            "??? ????????? ???????????? ?????? ??? ?????? ????????? ????????? ????????????.\n" +
            "<???????????? ???????????? ??? ??????>\n" +
            "<???????????? ???????????? ??? ??????>??? ????????? ??????????????? ??????.????????? ?????? ?????????????????? <?????? ?????? ???>?????? ??? ??????????????? ????????? ??????.???????????????.\n" +
            "???????????? : ??????????????????????????? ???15??? (??????????????? ??????????????) ???1???\n" +
            "???????????? : ??????????????? ??????/?????? ??? ?????? ?????? ?????? ?????? : 3???\n" +
            "???????????? : ??????????????? ?????? ????????? ????????? ?????? ?????? ?????? ???????????? ???????????? ???????????? ??????\n" +
            "\n" +
            "???3???(???????????? ??????????????? ??????)\n" +
            "\n" +
            "??? < HBC >???(???) ????????? ???????????? ????????? ???????????? ????????????.\n" +
            "< ???????????? ???????????? ??? ?????? >\n" +
            "???????????? : ??????????????????, ????????????, ????????? ?????? ??????, ?????? ??????, ?????? IP ??????\n" +
            "???????????? : ????????????, ?????????ID, ??????\n" +
            "\n" +
            "???4???(??????????????? ???????????? ??? ????????????)\n" +
            "??? < HBC > ???(???) ???????????? ??????????????? ??????, ???????????? ?????? ??? ??????????????? ??????????????? ????????? ????????? ???????????? ?????? ??????????????? ???????????????.\n" +
            "??? ????????????????????? ???????????? ???????????? ??????????????? ??????????????? ??????????????? ????????????????????? ???????????? ?????? ????????? ?????? ??????????????? ?????? ??????????????? ?????? ????????????, ?????? ??????????????? ????????? ??????????????????(DB)??? ???????????? ??????????????? ???????????? ???????????????.\n" +
            "??? ???????????? ????????? ?????? ??? ????????? ????????? ????????????.\n" +
            "1. ????????????\n" +
            "< HBC > ???(???) ?????? ????????? ????????? ??????????????? ????????????, < HBC > ??? ???????????? ?????????????????? ????????? ?????? ??????????????? ???????????????.\n" +
            "2. ????????????\n" +
            "????????? ?????? ????????? ????????? ????????? ????????? ??? ?????? ????????? ????????? ???????????????.\n" +
            "????????? ????????? ??????????????? ???????????? ??????????????? ????????? ????????? ???????????????.\n" +
            "\n" +
            "???5???(??????????????? ?????????????????? ?????????????? ??? ??? ??????????????? ?????? ??????)\n" +
            "??? ??????????????? HBC??? ?????? ???????????? ???????????? ???????????????????????????????????? ?????? ?????? ????????? ????????? ??? ????????????.\n" +
            "??? ???1?????? ?????? ?????? ?????????HBC??? ?????? ??????????????? ???????????? ????????? ???41??????1?????? ?????? ??????, ????????????, ????????????(FAX) ?????? ????????? ?????? ??? ????????? HBC???(???) ?????? ?????? ?????? ?????? ?????????????????????.\n" +
            "??? ???1?????? ?????? ?????? ????????? ??????????????? ????????????????????? ????????? ?????? ??? ??? ???????????? ????????? ?????? ??? ????????????.??? ?????? ??????????????? ?????? ????????? ?????? ??????(???2020-7???)??? ?????? ???11??? ????????? ?????? ???????????? ?????? ????????? ?????????.\n" +
            "??? ???????????? ?????? ??? ???????????? ????????? ??????????????? ???????????? ???35??? ???4???, ???37??? ???2?????? ????????? ??????????????? ????????? ?????? ??? ??? ????????????.\n" +
            "??? ??????????????? ?????? ??? ?????? ????????? ?????? ???????????? ??? ??????????????? ?????? ???????????? ???????????? ?????? ???????????? ??? ????????? ????????? ??? ????????????.\n" +
            "??? HBC???(???) ???????????? ????????? ?????? ????????? ??????, ????????????????? ??????, ??????????????? ?????? ??? ?????? ??? ????????? ??? ?????? ??????????????? ????????? ?????????????????? ???????????????.\n" +
            "\n" +
            "???6???(??????????????? ????????? ??????????????? ?????? ??????)\n" +
            "< HBC >???(???) ??????????????? ????????? ????????? ?????? ????????? ?????? ????????? ????????? ????????????.\n" +
            "1. ???????????? ?????? ?????? ??????\n" +
            "???????????? ?????? ?????? ????????? ????????? ?????? ?????????(?????? 1???)?????? ?????? ????????? ???????????? ????????????.\n" +
            "2. ???????????? ?????? ????????? ????????? ??? ??????\n" +
            "??????????????? ???????????? ????????? ???????????? ???????????? ???????????? ????????? ?????? ??????????????? ???????????? ????????? ???????????? ????????????.\n" +
            "3. ????????????????????? ?????? ??? ??????\n" +
            "??????????????? ????????? ????????? ????????? ????????????????????? ???????????? ???????????? ????????????.\n" +
            "4. ?????? ?????? ????????? ????????? ??????\n" +
            "<HBC>('HBC')??? ???????????? ????????? ???????????? ?????? ?????? ???????????? ?????? ??? ????????? ?????? ????????? ????????????????????? ???????????? ???????????? ????????????????? ?????? ??????????????? ????????? ????????? ????????? ???????????? ???????????? ?????????/??????????????? ?????? ??? ???????????? ????????????.\n" +
            "5. ??????????????? ?????????\n" +
            "???????????? ??????????????? ??????????????? ????????? ?????? ?????? ??? ???????????? ??????, ???????????? ??? ??? ????????? ????????? ???????????? ?????? ??? ?????? ???????????? ????????? ????????? ?????? ?????? ????????? ???????????? ?????? ?????? ??????????????? ???????????? ????????????.\n" +
            "6. ??????????????? ?????? ??? ????????? ??????\n" +
            "?????????????????????????????? ????????? ????????? ?????? 1??? ?????? ??????, ???????????? ?????????,??????, 5?????? ????????? ??????????????? ????????? ??????????????? ???????????????, ?????????????????? ?????? ??????????????? ???????????? ???????????? 2????????? ??????, ???????????? ????????????.\n" +
            "??????, ??????????????? ????????? ??? ??????, ???????????? ????????? ??????????????? ???????????? ????????????.\n" +
            "7. ??????????????? ?????? ?????? ??????\n" +
            "??????????????? ???????????? ?????????????????????????????? ?????? ??????????????? ??????,??????,????????? ????????? ??????????????? ?????? ??????????????? ????????? ????????? ????????? ?????? ????????? ???????????????????????? ???????????? ?????????????????? ?????? ????????? ???????????? ????????????.\n" +
            "\n" +
            "8. ??????????????? ?????? ???????????? ??????\n" +
            "??????????????? ????????? ??????, ?????????????????? ?????? ??????????????? ?????? ????????? ????????? ???????????? ????????????.\n" +
            "9. ??????????????? ?????? ?????? ??????\n" +
            "??????????????? ???????????? ?????? ????????? ?????? ????????? ????????? ?????? ?????? ?????? ???????????? ????????? ??????, ???????????? ????????????.\n" +
            "\n" +
            "???7???(??????????????? ???????????? ???????????? ????????? ?????????????? ??? ??? ????????? ?????? ??????)\n" +
            "HBC ???(???) ??????????????? ??????????????? ???????????? ????????? ???????????? ?????????(cookie)?????? ???????????? ????????????.\n" +
            "\n" +
            "???8??? (???????????? ?????????????????? ?????? ??????)\n" +
            "??? HBC ???(???) ???????????? ????????? ?????? ????????? ???????????? ????????????, ???????????? ????????? ????????? ??????????????? ???????????? ??? ???????????? ?????? ????????? ????????? ?????? ???????????? ?????? ???????????? ???????????? ????????????.\n" +
            "??? ???????????? ?????? ?????????\n" +
            "?????? :?????????\n" +
            "?????? :???????????? ?????? ?????????\n" +
            "?????? :CTO\n" +
            "????????? :010-9128-5708, ksp970306@gmail.com,\n" +
            "??? ???????????? ?????? ?????? ????????? ???????????????.\n" +
            "??? ???????????? ?????? ?????? ??????\n" +
            "????????? :???????????? ?????? ??????\n" +
            "????????? :?????????\n" +
            "????????? :010-9128-5708, ksp970306@gmail.com,\n" +
            "??? ????????????????????? HBC ??? ?????????(?????? ??????)??? ?????????????????? ????????? ?????? ???????????? ?????? ?????? ??????, ????????????, ???????????? ?????? ?????? ????????? ???????????? ??????????????? ??? ??????????????? ???????????? ??? ????????????. HBC ???(???) ??????????????? ????????? ?????? ?????? ?????? ?????? ??? ??????????????? ????????????.\n" +
            "???9???(??????????????? ??????????????? ???????????????????? ??????)\n" +
            "??????????????? ??????????????? ???????????? ???35?????? ?????? ??????????????? ?????? ????????? ????????? ????????? ??? ??? ????????????.\n" +
            "< HBC >???(???) ??????????????? ???????????? ??????????????? ???????????? ??????????????? ?????????????????????.\n" +
            "??? ???????????? ???????????? ?????????????? ??????\n" +
            "????????? : ???????????? ?????? ??????\n" +
            "????????? : ?????????\n" +
            "????????? : 010-9128-5708, ksp970306@gmail.com,\n" +
            "\n" +
            "???10???(??????????????? ??????????????? ?????? ????????????)\n" +
            "??????????????? ????????????????????? ?????? ????????? ?????? ????????? ?????????????????????????????????, ???????????????????????? ?????????????????????????????? ?????? ?????????????????? ?????? ?????? ????????? ??? ????????????. ??? ?????? ?????? ????????????????????? ??????, ????????? ???????????? ????????? ????????? ??????????????? ????????????.\n" +
            "\n" +
            "1. ????????????????????????????????? : (????????????) 1833-6972 (www.kopico.go.kr)\n" +
            "2. ?????????????????????????????? : (????????????) 118 (privacy.kisa.or.kr)\n" +
            "3. ???????????? : (????????????) 1301 (www.spo.go.kr)\n" +
            "4. ????????? : (????????????) 182 (ecrm.cyber.go.kr)\n" +
            "\n" +
            "??????????????????????????????35???(??????????????? ??????), ???36???(??????????????? ??????????????), ???37???(??????????????? ???????????? ???)??? ????????? ?????? ????????? ??? ?????? ??????????????? ?????? ?????? ?????? ?????? ???????????? ????????? ?????? ?????? ????????? ????????? ?????? ?????? ?????????????????? ????????? ?????? ?????? ??????????????? ????????? ??? ????????????.\n" +
            "\n" +
            "??? ??????????????? ?????? ????????? ????????? ???????????????????????????(www.simpan.go.kr) ??????????????? ??????????????? ????????????.\n" +
            "\n" +
            "???11???(???????????? ???????????? ??????)\n" +
            "??? ??? ??????????????????????????? 2022??? 11??? 15????????? ???????????????.\n" +
            "\n"
}