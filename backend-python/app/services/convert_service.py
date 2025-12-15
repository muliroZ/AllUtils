import os
import tempfile
from pdf2docx import Converter

def convert_pdf_to_docx(pdf_content: bytes):
    
    with tempfile.NamedTemporaryFile(suffix=".pdf", delete=False) as temp_pdf,\
          tempfile.NamedTemporaryFile(suffix=".docx", delete=False) as temp_docx:
        
        temp_pdf_path = temp_pdf.name
        temp_docx_path = temp_docx.name

        try:
            temp_pdf.write(pdf_content)
            temp_pdf.flush()

            cnv = Converter(temp_pdf_path)
            cnv.convert(temp_docx_path)
            cnv.close()

            with open(temp_docx_path, "rb") as f:
                docx_content = f.read()

            return docx_content, os.path.basename(temp_docx_path)
        
        finally:
            if os.path.exists(temp_pdf_path):
                os.remove(temp_pdf_path)
            if os.path.exists(temp_docx_path):
                os.remove(temp_docx_path)