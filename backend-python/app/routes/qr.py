from fastapi import APIRouter, Response, Request
from ..services.qr_service import gerar_qr

router = APIRouter(prefix="/qr", tags=["QR Code"])

@router.post("/")
async def gerar(request: Request):
    body_bytes = await request.body()
    texto = body_bytes.decode("utf-8")

    if not texto:
        return Response(content=b"", status_code=400)

    img_bytes = gerar_qr(texto)
    return Response(content=img_bytes, media_type="image/png")
