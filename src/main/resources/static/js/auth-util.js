function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)).join(''));
        return JSON.parse(jsonPayload);
    } catch (e) {
        return null;
    }
}

function hasPermission(permission) {
    const token = localStorage.getItem('jwtToken');
    if (!token) return false;

    const payload = parseJwt(token);
    if (!payload || !payload.role) return false;

    return payload.role === permission;
}
