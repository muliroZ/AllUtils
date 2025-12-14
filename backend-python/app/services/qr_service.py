import qrcode
import io

def gerar_qr(texto: str) -> bytes:
    img = qrcode.make(texto)
    buffer = io.BytesIO()
    img.save(buffer, "PNG")
    return buffer.getvalue()
