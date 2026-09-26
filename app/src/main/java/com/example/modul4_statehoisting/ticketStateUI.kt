package com.example.modul4_statehoisting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TicketBookingScreen() {
    var namaPemesan by rememberSaveable { mutableStateOf("") }
    var jumlahTiket by rememberSaveable { mutableIntStateOf(1) }

    var statusPesan by remember { mutableStateOf("") }
    var triggerProcess by remember { mutableStateOf(false) }
    var isProcessing by remember { mutableStateOf(false) }

    val hargaTiketPerLembar = 50000
    val totalBayar = hargaTiketPerLembar * jumlahTiket

    LaunchedEffect(triggerProcess) {
        if (triggerProcess) {
            isProcessing = true
            statusPesan = "Status: Memproses pesanan.........."
            delay(5000)
            statusPesan = "Status: Tiket telah dipesan"
            isProcessing = false
            triggerProcess = false
        }
    }

    TicketBookingContent(
        nama = namaPemesan,
        onNamaChange = { namaPemesan = it },
        jumlahTiket = jumlahTiket,
        onTambahTiket = { jumlahTiket++ },
        onKurangTiket = { if (jumlahTiket > 1) jumlahTiket-- },
        hargaPerTiket = hargaTiketPerLembar,
        totalBayar = totalBayar,
        statusPesan = statusPesan,
        isProcessing = isProcessing,
        onPesanClicked = {
            if (namaPemesan.trim().isEmpty()) {
                statusPesan = "Status: Nama Masih Kosong"
            } else {
                triggerProcess = true
            }
        }
    )
}

@Composable
fun TicketBookingContent(
    nama: String,
    onNamaChange: (String) -> Unit,
    jumlahTiket: Int,
    onTambahTiket: () -> Unit,
    onKurangTiket: () -> Unit,
    hargaPerTiket: Int,
    totalBayar: Int,
    statusPesan: String,
    isProcessing: Boolean,
    onPesanClicked: () -> Unit
) {
    val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID")).apply {
        maximumFractionDigits = 0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "🎫", fontSize = 42.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Pemesanan Tiket",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Modul 4 - State Hoisting & Side Effect",
                color = Color(0xFF94A3B8),
                fontSize = 13.sp
            )
        }

        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Nama Pembeli Tiket",
                            color = Color(0xFF64748B),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = nama,
                            onValueChange = onNamaChange,
                            placeholder = { Text("Masukkan nama lengkap...") },
                            singleLine = true,
                            enabled = !isProcessing,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Jumlah Tiket",
                                color = Color(0xFF64748B),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${formatRupiah.format(hargaPerTiket)} / tiket",
                                color = Color(0xFF0284C7),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = onKurangTiket,
                                enabled = !isProcessing && jumlahTiket > 1,
                                modifier = Modifier.size(44.dp),
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
                            ) {
                                Text("-", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 16.dp)
                                    .height(44.dp)
                                    .background(Color(0xFFF1F5F9), shape = RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$jumlahTiket",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                            }

                            Button(
                                onClick = onTambahTiket,
                                enabled = !isProcessing,
                                modifier = Modifier.size(44.dp),
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
                            ) {
                                Text("+", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Total Bayar",
                            color = Color(0xFF64748B),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = formatRupiah.format(totalBayar),
                            color = Color(0xFF16A34A),
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // 4. TOMBOL PESAN TIKET
                Button(
                    onClick = onPesanClicked,
                    enabled = !isProcessing,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
                ) {
                    if (isProcessing) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Memproses...", color = Color.White, fontWeight = FontWeight.Bold)
                    } else {
                        Text("Pesan Tiket", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }

                if (statusPesan.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = when {
                                statusPesan.contains("Kosong") -> Color(0xFFFEF2F2)
                                statusPesan.contains("Memproses") -> Color(0xFFEFF6FF)
                                else -> Color(0xFFF0FDF4)
                            }
                        )
                    ) {
                        Text(
                            text = statusPesan,
                            color = when {
                                statusPesan.contains("Kosong") -> Color(0xFFDC2626)
                                statusPesan.contains("Memproses") -> Color(0xFF2563EB)
                                else -> Color(0xFF16A34A)
                            },
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTicketBookingScreen() {
    TicketBookingScreen()
}