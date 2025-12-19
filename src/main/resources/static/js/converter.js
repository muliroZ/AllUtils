document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("converterForm");
  const fileInput = document.getElementById("pdfFile");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const file = fileInput.files[0];
    if (!file) {
      alert("Por favor, selecione um arquivo PDF para converter.");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await fetch("/api/v1/convert/pdf-to-docx", {
        method: "POST",
        body: formData,
      });

      if (!response.ok) {
        throw new Error("Erro na conversão do arquivo.");
      }

      const blob = await response.blob();
      const downloadUrl = window.URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = downloadUrl;
      a.download = file.name.replace(/\.pdf$/i, ".docx");
      document.body.appendChild(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(downloadUrl);
    } catch (error) {
      alert("Ocorreu um erro ao converter o arquivo: " + error.message);
    }
  });
});
