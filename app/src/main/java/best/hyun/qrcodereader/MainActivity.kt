package best.hyun.qrcodereader

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import best.hyun.qrcodereader.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class MainActivity : AppCompatActivity() {
    val TAG: String = "로그"
    val CAMERAPERMISSIONCODE = 1

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: QRAdapter

    // 스캔 결과 처리하기
    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        // 스캔을 성공하면 null이 아니다
        if (result.contents == null) {
            // 스캔을 하지 않았을 때
            Log.d(TAG, "MainActivity - () called // 스캔 취소!")
        } else {
            // 스캔을 성공했을 때
            Log.d(TAG, "MainActivity - () called // 스캔 성공! \n${result.contents}")

            // QR 코드 URL 기록 남기기
            adapter.addData(result.contents)

            // 핸드폰 기본 앱으로 해당 URL 연결하기
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("${result.contents}"))
//            startActivity(intent)
        }
    }

    // 뷰가 생성되었을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
/*
        // 권한 요청하기
        when {
            // 권한을 이미 획득한 상태일 때 실행되는 부분
            ContextCompat.checkSelfPermission(
                this@MainActivity,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                Log.d(TAG, "MainActivity - onCreate() called // checkPermission 실행")
            }
            // 앱에 권한이 필요한 이유와 거부될 경우 비활성화되는 기능에 대해 사용자에게 설명하는 부분
            // 권한을 부여하지 않고 실행할 수 있도록 하는 버튼이 포함될 수 있음
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                // showInContextUI()
                Log.d(TAG, "MainActivity - onCreate() called // showInContextUI() ")
            }
            // 직접 권한을 요청하는 부분
            else -> {
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissions(
                    arrayOf(android.Manifest.permission.CAMERA),
                    CAMERAPERMISSIONCODE
                )
                Log.d(TAG, "MainActivity - onCreate() called // requestPermission")
            }
        }
*/

        adapter = QRAdapter()
        val layoutManager = LinearLayoutManager(this@MainActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.activityMainRecyclerView.layoutManager = layoutManager
        binding.activityMainRecyclerView.adapter = adapter

        // QR 코드 인식 버튼
        binding.activityMainBtnScan.setOnClickListener {
            // QR 코드 인식 카메라 실행
            onScan(it)
        }
    }

    // Launch
    fun onScan(view: View?) {
        // Register the launcher and result handler
        val options = ScanOptions()
        // 스캔 화면에 보여지는 텍스트
//        options.setPrompt("")
        options.setOrientationLocked(false)
        options.setBeepEnabled(false)
        options.captureActivity = BarcodeActivity::class.java

        barcodeLauncher.launch(options)
    }

    /*
    // 권한 부여 결과
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERAPERMISSIONCODE -> {
                // 권한이 거절되었으면 배열이 비어있다
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // 권한을 얻었으니 계속 작업하면 된다
                    Log.d(TAG, "MainActivity - onRequestPermissionsResult() called // 권한이 부여됨")

                } else {
                    // 기능을 사용하려면 사용자가 거부한 권한이 필요하므로 해당 기능을 사용할 수 없음을 사용자에게 설명한다.
                    // 사용자의 결정을 존중하며 결정을 변경하도록 설득하기 위해 시스템 설정에 연결하지 마라. - 안드로이드 문서
                    Log.d(TAG, "MainActivity - onRequestPermissionsResult() called // 권한이 거부됨")
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
                Log.d(TAG, "MainActivity - onRequestPermissionsResult() called // defualt 권한 부여")
            }
        }
    }
*/


    class QRAdapter : RecyclerView.Adapter<QRAdapter.ViewHolder>() {

        val dataSet = ArrayList<String>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
            return ViewHolder(view).also { holder ->
                holder.btn.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(holder.btn.text.toString()))
                    parent.context.startActivity(intent)
                }
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(dataSet[position])
        }

        override fun getItemCount(): Int {
            return dataSet.size
        }

        fun addData(data: String) {
            dataSet.add(data)
            notifyDataSetChanged()
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val btn = view.findViewById<Button>(R.id.itemRowBtn)

            fun bind(data: String) {
                btn.text = data
            }
        }
    }
}