const API_URL = "/api/fila";

const statusLabels = {
    AGUARDANDO: "Waiting",
    CHAMADO: "Called",
    ATENDIDO: "Served"
};

const serviceLabels = {
    Clinica: "Clinic",
    Barbearia: "Barbershop",
    "Assistencia tecnica": "Technical support",
    Secretaria: "Administrative office"
};

const formFila = document.getElementById("formFila");
const mensagem = document.getElementById("mensagem");
const corpoTabela = document.getElementById("corpoTabela");
const resumoFila = document.getElementById("resumoFila");
const btnAtualizar = document.getElementById("btnAtualizar");
const btnChamarProximo = document.getElementById("btnChamarProximo");

function mostrarMensagem(texto, tipo = "") {
    mensagem.textContent = texto;
    mensagem.className = `mensagem ${tipo}`.trim();
}

async function lerResposta(response) {
    if (response.status === 204) {
        return null;
    }

    const conteudo = await response.json();

    if (!response.ok) {
        const erro = conteudo.mensagens ? conteudo.mensagens.join(" ") : "Unexpected error.";
        throw new Error(erro);
    }

    return conteudo;
}

async function carregarFila() {
    try {
        const response = await fetch(API_URL);
        const pessoas = await lerResposta(response);
        desenharTabela(pessoas);
    } catch (error) {
        corpoTabela.innerHTML = `<tr><td colspan="6">${error.message}</td></tr>`;
    }
}

function desenharTabela(pessoas) {
    resumoFila.textContent = `${pessoas.length} registered`;

    if (pessoas.length === 0) {
        corpoTabela.innerHTML = '<tr><td colspan="6">Nobody has joined the queue yet.</td></tr>';
        return;
    }

    corpoTabela.innerHTML = pessoas.map((pessoa, index) => `
        <tr>
            <td>${index + 1}</td>
            <td>${pessoa.nome}</td>
            <td>${pessoa.telefone}</td>
            <td>${Object.hasOwn(serviceLabels, pessoa.tipoAtendimento) ? serviceLabels[pessoa.tipoAtendimento] : pessoa.tipoAtendimento}</td>
            <td><span class="status ${pessoa.status}">${statusLabels[pessoa.status]}</span></td>
            <td>
                <div class="acoes">
                    ${botaoAtendido(pessoa)}
                    <button class="botao perigo pequeno" type="button" onclick="removerPessoa(${pessoa.id})">
                        Remove
                    </button>
                </div>
            </td>
        </tr>
    `).join("");
}

function botaoAtendido(pessoa) {
    if (pessoa.status !== "CHAMADO") {
        return "";
    }

    return `
        <button class="botao pequeno" type="button" onclick="marcarAtendido(${pessoa.id})">
            Mark as served
        </button>
    `;
}

formFila.addEventListener("submit", async (event) => {
    event.preventDefault();

    const dados = {
        nome: formFila.nome.value,
        telefone: formFila.telefone.value,
        tipoAtendimento: formFila.tipoAtendimento.value
    };

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dados)
        });

        await lerResposta(response);
        formFila.reset();
        mostrarMensagem("Person added to the queue.", "sucesso");
        carregarFila();
    } catch (error) {
        mostrarMensagem(error.message, "erro");
    }
});

btnChamarProximo.addEventListener("click", async () => {
    try {
        const response = await fetch(`${API_URL}/chamar-proximo`, { method: "PATCH" });
        const pessoa = await lerResposta(response);
        mostrarMensagem(`${pessoa.nome} has been called.`, "sucesso");
        carregarFila();
    } catch (error) {
        mostrarMensagem(error.message, "erro");
    }
});

btnAtualizar.addEventListener("click", carregarFila);

async function marcarAtendido(id) {
    try {
        const response = await fetch(`${API_URL}/${id}/atendido`, { method: "PATCH" });
        await lerResposta(response);
        mostrarMensagem("Service completed.", "sucesso");
        carregarFila();
    } catch (error) {
        mostrarMensagem(error.message, "erro");
    }
}

async function removerPessoa(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });
        await lerResposta(response);
        mostrarMensagem("Person removed from the queue.", "sucesso");
        carregarFila();
    } catch (error) {
        mostrarMensagem(error.message, "erro");
    }
}

carregarFila();
