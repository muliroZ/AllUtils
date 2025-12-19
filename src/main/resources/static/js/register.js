document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("registerForm");
  const messageDiv = document.getElementById("message");

  form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const data = {
      name: username,
      email: email,
      password: password,
    };

    try {
      const response = await fetch("/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });
      if (response.ok) {
        messageDiv.textContent =
          "Cadastro realizado com sucesso! Redirecionando...";
        messageDiv.style.color = "green";
        form.reset();
        setTimeout(() => {
          window.location.href = "/login.html";
        }, 2000);
      } else {
        const errorResult = await response.json();
        messageDiv.textContent =
          "Cadastro falhou: " + (errorResult.message || "Erro desconhecido.");
        messageDiv.style.color = "red";
      }
    } catch (error) {
      alert("Ocorreu um erro: " + error.message);
    }
  });
});
